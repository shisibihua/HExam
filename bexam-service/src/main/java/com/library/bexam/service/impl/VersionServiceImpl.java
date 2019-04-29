package com.library.bexam.service.impl;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.dao.VersionDao;
import com.library.bexam.entity.VersionEntity;
import com.library.bexam.form.VersionForm;
import com.library.bexam.service.VersionService;
import com.library.bexam.util.ConvertResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VersionServiceImpl implements VersionService{
    @Autowired
    private VersionDao versionDao;
    @Override
    public Result searchVersonList(int subjectId) {
        if(subjectId==0){
            return ConvertResult.getParamErrorResult("科目id不能为空");
        }
        List<VersionEntity> versionList=versionDao.getVersionListBySubId(subjectId);
        Map<String,Object> result=new HashMap<>();
        if(versionList!=null && !versionList.isEmpty()){
            List<VersionForm> versionFormList=new ArrayList<>();
            for(VersionEntity version:versionList){
                VersionForm versionForm=new VersionForm();
                versionForm.setVersionId(version.getVersionId());
                versionForm.setVersionName(version.getVersionName());
                versionForm.setCreateTime(version.getCreateTime());
                versionFormList.add(versionForm);
            }
            result.put("version",versionFormList);
            result.put("totalCount",versionFormList.size());
        }else{
            result.put("version",new ArrayList<>());
            result.put("totalCount",0);
        }
        return ConvertResult.getSuccessResult(result);
    }
}
