package com.library.bexam.service.impl;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.common.util.DateUtil;
import com.library.bexam.dao.ClassDao;
import com.library.bexam.dao.GradeDao;
import com.library.bexam.dao.PeriodDao;
import com.library.bexam.dao.SubjectDao;
import com.library.bexam.entity.ClassEntity;
import com.library.bexam.entity.PeriodEntity;
import com.library.bexam.service.PeriodService;
import com.library.bexam.util.ConvertResult;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PeriodServiceImpl implements PeriodService{
    private Logger logger= LoggerFactory.getLogger(PeriodServiceImpl.class);
    @Autowired
    private PeriodDao periodDao;
    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private ClassDao classDao;
    @Autowired
    private SubjectDao subjectDao;

    @Override
    public Result addPeriod(PeriodEntity periodEntity) {
        if(periodEntity==null || StringUtil.isEmpty(periodEntity.getPeriodName()) || periodEntity.getSchoolId()==0){
            return ConvertResult.getParamErrorResult("学段名称或学校id不能为空");
        }
        periodEntity.setCreateTime(DateUtil.currentDatetime());
        return ConvertResult.getSuccessResult(periodDao.addPeriod(periodEntity));
    }

    @Override
    public Result updatePeriod(PeriodEntity periodEntity) {
        if(periodEntity==null || StringUtil.isEmpty(periodEntity.getPeriodName()) || periodEntity.getPeriodId()==0){
            return ConvertResult.getParamErrorResult("学段名称或id不能为空");
        }
        return ConvertResult.getSuccessResult(periodDao.updatePeriod(periodEntity));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result deletePeriodById(@RequestBody Map<String,String> periodEntity) {
        if(periodEntity==null || !periodEntity.containsKey("periodId") || StringUtil.isEmpty(periodEntity.get("periodId"))){
            return ConvertResult.getParamErrorResult("学段ids不能为空");
        }
        String periodId= periodEntity.get("periodId");
        //删除学段,还要删除与学段关联的年级、班级、科目信息
        String[] periodIdArr=periodId.split(",");
        List<ClassEntity> classList=classDao.searchClassListByPeriodIds(periodIdArr);
        if(classList!=null && !classList.isEmpty()){
            //删除班级与学生的关系
            classDao.deleteClass2Student(classList);
            //首先删除班级信息
            classDao.deleteClassByPeriodIds(periodIdArr);
        }
        try {
            //删除年级信息
            gradeDao.deleteGradeByPeriodIds(periodIdArr);
            //删除学段与科目关系
            subjectDao.deletePeriod2SubjectByPeriodIds(periodIdArr);
            //删除学段信息
            periodDao.deletePeriodByIds(periodIdArr);
            return ConvertResult.getSuccessResult(true);
        }catch (Exception e){
            logger.error("删除学段失败,periodIds="+periodId,e);
            return ConvertResult.getErrorResult("删除学段失败");
        }
    }

    @Override
    public Result searchPeriodList() {
        try {
            List<PeriodEntity> periodList=periodDao.searchPeriodList();
            if (periodList != null && !periodList.isEmpty()) {
                for(PeriodEntity period:periodList){
                    period.setSchoolName(period.getSchoolEntity()==null?"":period.getSchoolEntity().getSchoolName());
                }
            }
            Map<String,Object> periodResult=new HashMap<>();
            periodResult.put("period",periodList);
            periodResult.put("totalCount",periodList.size());
            return ConvertResult.getSuccessResult(periodResult);
        }catch (Exception e){
            logger.error("查询学段列表异常",e);
            return ConvertResult.getErrorResult("查询学段列表失败");
        }
    }

    @Override
    public Result getPeriodById(int periodId) {
        if(periodId==0){
            return ConvertResult.getParamErrorResult("学段id不能为空");
        }
        try {
            PeriodEntity periodEntity = periodDao.searchPeriodById(periodId);
            if (periodEntity != null) {
                periodEntity.setSchoolName(periodEntity.getSchoolEntity().getSchoolName());
            }
            return ConvertResult.getSuccessResult(periodEntity);
        }catch (Exception e){
            logger.error("根据学段id查询学段信息异常,periodId="+periodId ,e);
            return ConvertResult.getErrorResult("根据学段id查询学段信息异常");
        }
    }
}
