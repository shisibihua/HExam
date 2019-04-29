package com.library.bexam.service.impl;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.common.util.DateUtil;
import com.library.bexam.common.util.TipsMessage;
import com.library.bexam.dao.ClassDao;
import com.library.bexam.dao.UserDao;
import com.library.bexam.entity.ClassEntity;
import com.library.bexam.form.ClassForm;
import com.library.bexam.service.ClassService;
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
public class ClassServiceImpl implements ClassService{
    @Autowired
    private ClassDao classDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Result addClass(ClassEntity classEntity) {
        if(classEntity==null || classEntity.getGradeId()==0 || classEntity.getPeriodId()==0 ||
                StringUtil.isEmpty(classEntity.getClassName())){
            return ConvertResult.getParamErrorResult("学段id或年级id或班级名称不能为空");
        }
        classEntity.setCreateTime(DateUtil.currentDatetime());
        return ConvertResult.getSuccessResult(classDao.addClass(classEntity));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result deleteClass(Map<String, Integer> classEntity) {
        if(classEntity==null || classEntity.isEmpty() || !classEntity.containsKey("classId") ||
                classEntity.get("classId")==0){
            return ConvertResult.getParamErrorResult("班级id不能为空");
        }
        //首先删除班级与学生的关系
        List<ClassEntity> classIdList=new ArrayList<>();
        ClassEntity classModel=new ClassEntity();
        classModel.setClassId(classEntity.get("classId"));
        classIdList.add(classModel);
        List<Integer> idList=new ArrayList<>();
        idList.add(classModel.getClassId());
        try{
            //删除班级与学生的关系
            classDao.deleteClass2Student(classIdList);
            //删除班级与用户的关系
            userDao.deleteCla2UserByClassId(idList);
            //删除班级
            return ConvertResult.getSuccessResult(classDao.deleteClassByClassIds(classIdList));
        }catch (Exception e){
            Result errorResult=new Result(TipsMessage.FAILED_CODE);
            return ConvertResult.getErrorResult("根据班级id删除班级失败",errorResult);
        }
    }

    @Override
    public Result updateClass(ClassEntity classEntity) {
        if(classEntity==null || classEntity.getPeriodId()==0 || classEntity.getPeriodId()==0 || classEntity.getClassId()==0 ||
                StringUtil.isEmpty(classEntity.getClassName())){
            return ConvertResult.getParamErrorResult("学段id或年级id或班级id或班级名称不能为空");
        }
        classEntity.setCreateTime(DateUtil.currentDatetime());
        try {
            return ConvertResult.getSuccessResult(classDao.updateClass(classEntity));
        }catch (Exception e){
            logger.error("修改班级信息异常",e);
            return ConvertResult.getErrorResult("修改班级信息异常");
        }
    }

    @Override
    public Result searchClassById(int classId) {
        if(classId==0){
            return ConvertResult.getParamErrorResult("班级id不能为空");
        }
        ClassEntity classEntity=classDao.searchClassById(classId);
        if(classEntity!=null) {
            classEntity.setGradeName(classEntity.getGradeEntity()==null?"":classEntity.getGradeEntity().getGradeName());
            classEntity.setPeriodName(classEntity.getPeriodEntity()==null?"":classEntity.getPeriodEntity().getPeriodName());
        }
        return ConvertResult.getSuccessResult(classEntity);
    }

    @Override
    public Result searchClassList(int gradeId, String search) {
        List<ClassEntity> classList=null;
        try {
            classList=classDao.searchClassList(gradeId, search);
            if(classList!=null && !classList.isEmpty()) {
                for(ClassEntity classEntity:classList){
                    classEntity.setGradeName(classEntity.getGradeEntity()==null?"":classEntity.getGradeEntity().getGradeName());
                    classEntity.setPeriodName(classEntity.getPeriodEntity()==null?"":classEntity.getPeriodEntity().getPeriodName());
                }
            }
        }catch (Exception e){
            logger.error("查询班级列表异常,gradeId与search可为空,gradeId="+gradeId+",search="+search);
            return ConvertResult.getErrorResult("查询班级列表异常");
        }
        Map<String,Object> result=new HashMap<>();
        result.put("class",classList);
        result.put("totalCount",classList.size());
        return ConvertResult.getSuccessResult(result);
    }

    @Override
    public Result searchClassesByUserId(int userId) {
        if(userId==0){
            return ConvertResult.getParamErrorResult("用户id不能为空");
        }
        return ConvertResult.getSuccessResult(classDao.searchClassesByUserId(userId));
    }

    @Override
    public Result allotClassToUser(Map<String, Object> params) {
        if(params==null || params.isEmpty() || !params.containsKey("userId") || !params.containsKey("classId")){
            return ConvertResult.getParamErrorResult("用户id或班级id不能为空");
        }
        int userId=(int)params.get("userId");
        String[] classIds=((String)params.get("classId")).split(",");
        List<ClassForm> classList=new ArrayList<>();
        List<String> classIdsList=userDao.searchClassListByUId(userId);;
        for(String classId:classIds) {
            if ((classIdsList==null || classIdsList.isEmpty()) || (classIdsList != null && !classIdsList.isEmpty() && !classIdsList.contains(classId))) {
                ClassForm classForm = new ClassForm();
                classForm.setClassId(Integer.parseInt(classId));
                classList.add(classForm);
            }
        }
        if(classList!=null && !classList.isEmpty()) {
            return ConvertResult.getSuccessResult(classDao.allotClassToUser(classList,userId));
        }else{
            return ConvertResult.getSuccessResult(false);
        }
    }
}
