package com.library.bexam.service.impl;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.common.util.DateUtil;
import com.library.bexam.common.util.TipsMessage;
import com.library.bexam.dao.ClassDao;
import com.library.bexam.dao.GradeDao;
import com.library.bexam.entity.ClassEntity;
import com.library.bexam.entity.GradeEntity;
import com.library.bexam.service.GradeService;
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
public class GradeServiceImpl implements GradeService{
    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private ClassDao classDao;
    @Override
    public Result addGrade(GradeEntity gradeEntity) {
        if(gradeEntity==null || gradeEntity.getPeriodId()==0 || StringUtil.isEmpty(gradeEntity.getGradeName())){
            return ConvertResult.getParamErrorResult("学段id或年级名称不能为空");
        }
        gradeEntity.setCreateTime(DateUtil.currentDatetime());
        return ConvertResult.getSuccessResult(gradeDao.addGrade(gradeEntity));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result deleteGrade(Map<String,String> gradeEntity) {
        if(gradeEntity==null || !gradeEntity.containsKey("gradeId") || StringUtil.isEmpty(gradeEntity.get("gradeId"))){
            return ConvertResult.getParamErrorResult("年级id不能为空");
        }
        String[] gradeIds=gradeEntity.get("gradeId").split(",");
        List<ClassEntity> classList=classDao.searchClassListByGradeIds(gradeIds);
        if(classList!=null && !classList.isEmpty()){
            //删除班级与学生的关系
            classDao.deleteClass2Student(classList);
            //删除班级
            classDao.deleteClassByClassIds(classList);
        }
        //删除年级
        return ConvertResult.getSuccessResult(gradeDao.deleteGradeByIds(gradeIds));
    }

    @Override
    public Result updateGrade(GradeEntity gradeEntity) {
        if(gradeEntity==null || gradeEntity.getGradeId()==0 || StringUtil.isEmpty(gradeEntity.getGradeName())){
            return ConvertResult.getParamErrorResult("年级id或年级名称不能为空");
        }
        return ConvertResult.getSuccessResult(gradeDao.updateGrade(gradeEntity));
    }

    @Override
    public Result searchGradeListByPeriodId(int periodId) {
        try {
            List<GradeEntity> gradeList=gradeDao.searchGradeListByPeriodId(periodId);
            if(gradeList!=null && !gradeList.isEmpty()){
                for(GradeEntity gradeEntity:gradeList){
                    gradeEntity.setPeriodName(gradeEntity.getPeriodEntity()==null?"":gradeEntity.getPeriodEntity().getPeriodName());
                }
                Map<String,Object> result=new HashMap<>();
                result.put("grade",gradeList);
                result.put("totalCount",gradeList.size());
                return ConvertResult.getSuccessResult(result);
            }else{
                return ConvertResult.getSuccessResult(new ArrayList<>());
            }
        }catch (Exception e){
            logger.error("根据学段id查询年级异常,periodId="+periodId,e);
            return ConvertResult.getErrorResult("根据学段id查询年级异常");
        }
    }

    @Override
    public Result searchGradeById(int gradeId) {
        if(gradeId==0){
            return ConvertResult.getParamErrorResult("年级id不能为空");
        }
        GradeEntity gradeEntity=gradeDao.searchGradeById(gradeId);
        gradeEntity.setPeriodName(gradeEntity.getPeriodEntity()==null?"":gradeEntity.getPeriodEntity().getPeriodName());
        return ConvertResult.getSuccessResult(gradeEntity);
    }
}
