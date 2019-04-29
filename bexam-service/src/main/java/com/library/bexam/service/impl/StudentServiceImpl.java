package com.library.bexam.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.library.bexam.common.pojo.model.Page;
import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.common.util.DateUtil;
import com.library.bexam.common.util.TipsMessage;
import com.library.bexam.common.util.UUIDUtil;
import com.library.bexam.dao.StudentDao;
import com.library.bexam.entity.*;
import com.library.bexam.form.ClassForm;
import com.library.bexam.form.GradeForm;
import com.library.bexam.form.OrgForm;
import com.library.bexam.form.PeriodForm;
import com.library.bexam.service.StudentService;
import com.library.bexam.util.ConvertResult;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alicp.jetcache.Cache.logger;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentDao studentDao;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result addStudent(StudentEntity studentEntity) {
        if(studentEntity==null || studentEntity.getClassId()==0 || StringUtil.isEmpty(studentEntity.getRealName())){
            return ConvertResult.getParamErrorResult("学生信息或班级id不能为空");
        }
        //添加学生
        studentEntity.setCreateTime(DateUtil.currentDatetime());
        if(studentEntity.getCode()==null){
            studentEntity.setCode(UUIDUtil.getUUID());
        }
        int stuId=studentDao.addStudent(studentEntity);
        Result errorResult=new Result(TipsMessage.FAILED_CODE);
        if(stuId>0){
            //保存学生与班级关系
            return ConvertResult.getSuccessResult(studentDao.addClass2Student(studentEntity.getClassId(),
                    studentEntity.getStudentId()));
        }else{
            return ConvertResult.getErrorResult("保存学生失败",errorResult);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result deleteStudent(Map<String, String> studentEntity) {
        if(studentEntity==null || studentEntity.isEmpty() || !studentEntity.containsKey("studentId") ||
                StringUtil.isEmpty(studentEntity.get("studentId"))){
            return ConvertResult.getParamErrorResult("学生ids不能为空");
        }
        //删除学生与班级的关系
        String[] studentIdsArr=studentEntity.get("studentId").split(",");
        studentDao.deleteClass2Student(studentIdsArr);
        //删除学生
        return ConvertResult.getSuccessResult(studentDao.deleteStudentByIds(studentIdsArr));
    }

    @Override
    public Result updateStudent(StudentEntity studentEntity) {
        if(studentEntity==null || studentEntity.getStudentId()==0){
            return ConvertResult.getParamErrorResult("学生id不能为空");
        }
        studentEntity.setCreateTime(DateUtil.currentDatetime());
        return ConvertResult.getSuccessResult(studentDao.updateStudent(studentEntity));
    }

    @Override
    public Result searchStudentById(int studentId) {
        if(studentId==0){
            return ConvertResult.getParamErrorResult("学生id不能为空");
        }
        return ConvertResult.getSuccessResult(studentDao.searchStudentById(studentId));
    }

    @Override
    public Result searchStudentList(int classId, String search) {
        List<StudentEntity> studentList = studentDao.searchStudentListByClassId(classId,search);
        Map<String,Object> result=new HashMap<>();
        result.put("student",studentList);
        result.put("totalCount",studentList.size());
        return ConvertResult.getSuccessResult(result);
    }

    @Override
    public Result searchPageList(int classId, String search, int page, int pageSize) {
        if(page==0 || pageSize==0){
            return ConvertResult.getParamErrorResult(null);
        }
        int totalCount=studentDao.getTotalCount();
        int firstIndex=(page-1)*pageSize;
        List<StudentEntity> studentList=studentDao.searchStudentByPage(classId,search,firstIndex,pageSize);
        Page pageResult=new Page(studentList,page,pageSize,totalCount);
        return ConvertResult.getSuccessResult(pageResult);
    }

    @Override
    public Result searchOrgTree(){
        try {
            SchoolEntity schoolEntity=studentDao.searchOrgTree();
            JSONObject school= JSONObject.parseObject(JSON.toJSONString(schoolEntity));
            OrgForm orgForm=new OrgForm();
            if(school!=null && !school.isEmpty()) {
                orgForm.setId((int)school.get("schoolId"));
                orgForm.setName(school.get("schoolName").toString());
                orgForm.setTag(school.get("schoolId") + "-" + TipsMessage.SCHOOL_TYPE);
                orgForm.setType(TipsMessage.SCHOOL_TYPE);
                JSONArray periodList=JSONArray.parseArray(school.get("periodEntityList").toString());
                List<OrgForm> orgFormList=new ArrayList<>();
                if(periodList!=null && !periodList.isEmpty()) {
                    for (Object p : periodList) {
                        JSONObject periodJson = JSONObject.parseObject(p.toString());
                        if (periodJson != null && !periodJson.isEmpty()) {
                            OrgForm periodForm = new OrgForm();
                            periodForm.setId((int) periodJson.get("periodId"));
                            periodForm.setName(periodJson.get("periodName") == null ? "" : periodJson.get("periodName").toString());
                            periodForm.setTag(periodJson.get("periodId") + "-" + TipsMessage.PERIOD_TYPE);
                            periodForm.setType(TipsMessage.PERIOD_TYPE);
                            if(periodForm.getId()!=0) {
                                orgFormList.add(periodForm);
                            }
                            JSONArray gradeList = JSONArray.parseArray(periodJson.get("gradeList").toString());
                            List<OrgForm> periodFormList = new ArrayList<>();
                            if(periodForm.getId()!=0 && gradeList!=null && !gradeList.isEmpty()) {
                                for (Object g : gradeList) {
                                    JSONObject gradeJson = JSONObject.parseObject(g.toString());
                                    OrgForm gradeForm = new OrgForm();
                                    gradeForm.setId((int) gradeJson.get("gradeId"));
                                    gradeForm.setName(gradeJson.get("gradeName") == null ? "" : gradeJson.get("gradeName").toString());
                                    gradeForm.setTag(gradeJson.get("gradeId") + "-" + TipsMessage.GRADE_TYPE);
                                    gradeForm.setType(TipsMessage.GRADE_TYPE);
                                    if(gradeForm.getId()!=0) {
                                        periodFormList.add(gradeForm);
                                    }
                                    JSONArray classList = JSONArray.parseArray(gradeJson.get("classEntityList").toString());
                                    List<OrgForm> classFormList = new ArrayList<>();
                                    if(gradeForm.getId()!=0 && classList!=null && !classList.isEmpty()) {
                                        for (Object c : classList) {
                                            JSONObject classJson = JSONObject.parseObject(c.toString());
                                            OrgForm classForm = new OrgForm();
                                            classForm.setId((int) classJson.get("classId"));
                                            classForm.setName(classJson.get("className") == null ? "" : classJson.get("className").toString());
                                            classForm.setTag(classJson.get("classId") + "-" + TipsMessage.CLASS_TYPE);
                                            classForm.setType(TipsMessage.CLASS_TYPE);
                                            classForm.setChildren(new ArrayList<>());
                                            if(classForm.getId()!=0) {
                                                classFormList.add(classForm);
                                            }
                                        }
                                        gradeForm.setChildren(classFormList);
                                    }
                                }
                                periodForm.setChildren(periodFormList);
                            }
                        }
                    }
                }
                orgForm.setChildren(orgFormList);
            }
            return ConvertResult.getSuccessResult(orgForm);
        }catch (Exception e){
            logger.error("查询组织机构树异常",e);
            return ConvertResult.getErrorResult("查询组织机构树异常");
        }
    }
}
