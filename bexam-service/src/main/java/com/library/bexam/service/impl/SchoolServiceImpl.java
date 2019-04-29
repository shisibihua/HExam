package com.library.bexam.service.impl;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.common.util.DateUtil;
import com.library.bexam.common.util.TipsMessage;
import com.library.bexam.dao.SchoolDao;
import com.library.bexam.entity.SchoolEntity;
import com.library.bexam.service.SchoolService;
import com.library.bexam.util.ConvertResult;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImpl implements SchoolService{
    @Autowired
    private SchoolDao schoolDao;
    @Override
    public Result addSchool(SchoolEntity schoolEntity) {
        if (schoolEntity==null || StringUtil.isEmpty(schoolEntity.getSchoolName())){
            return ConvertResult.getParamErrorResult("学校信息不存在");
        }
        SchoolEntity schoolModel=schoolDao.searchSchoolByName(schoolEntity.getSchoolName());
        if(schoolModel==null){
            schoolEntity.setCreateTime(DateUtil.currentDatetime());
            return ConvertResult.getSuccessResult(schoolDao.addSchool(schoolEntity));
        }else{
            Result errorResult=new Result(TipsMessage.FAILED_CODE);
            return ConvertResult.getErrorResult("学校已存在",errorResult);
        }
    }

    @Override
    public Result updateSchool(SchoolEntity schoolEntity) {
        if(schoolEntity==null || schoolEntity.getSchoolId()==0){
            return ConvertResult.getParamErrorResult("学校id或名称不能为空");
        }
        return ConvertResult.getSuccessResult(schoolDao.updateSchool(schoolEntity));
    }

    @Override
    public Result searchSchool() {
        return ConvertResult.getSuccessResult(schoolDao.searchSchool());
    }

    @Override
    public Result searchSchoolById(int schoolId) {
        if(schoolId==0){
            return ConvertResult.getParamErrorResult("学校id不能为空");
        }
        return ConvertResult.getSuccessResult(schoolDao.searchSchoolById(schoolId));
    }
}
