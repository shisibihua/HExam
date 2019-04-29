package com.library.bexam.service.impl;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.dao.PointDao;
import com.library.bexam.entity.PointEntity;
import com.library.bexam.form.PointForm;
import com.library.bexam.service.PointService;
import com.library.bexam.util.ConvertResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PointServiceImpl implements PointService{
    @Autowired
    private PointDao pointDao;

    @Override
    public Result searchPointListBySubjectId(int subjectId) {
        if(subjectId==0){
            return ConvertResult.getParamErrorResult("科目id不能为空");
        }
        List<PointEntity> pointList=pointDao.searchPointListBySubId(subjectId);
        Map<String,Object> result=new HashMap<>();
        if(pointList!=null && !pointList.isEmpty()){
            List<PointEntity> allPointList=getAllPointList(pointList);
            result.put("point",allPointList);
            result.put("totalCount",allPointList.size());
        }else{
            result.put("point",new ArrayList<>());
            result.put("totalCount",0);
        }
        return ConvertResult.getSuccessResult(result);
    }

    /**
     * 获取知识点树形结构
     * @param pointList
     * @return
     */
    private List<PointEntity> getAllPointList(List<PointEntity> pointList){
        List<PointEntity> childPointList=pointDao.searchChildPointList(pointList);
        for(PointEntity point:pointList){
            point.setSubjectId(point.getSubjectForm()==null?0:point.getSubjectForm().getSubjectId());
            point.setSubjectName(point.getSubjectForm()==null?"":point.getSubjectForm().getSubjectName());
            if(childPointList!=null && !childPointList.isEmpty()){
                List<PointEntity> childPointEntityList=new ArrayList<>();
                for(PointEntity childPoint:childPointList){
                    if(point.getPointId()==childPoint.getParentId()) {
                        childPoint.setSubjectId(childPoint.getSubjectForm()==null?0:
                                childPoint.getSubjectForm().getSubjectId());
                        childPoint.setSubjectName(childPoint.getSubjectForm()==null?"":
                                childPoint.getSubjectForm().getSubjectName());
                        childPointEntityList.add(childPoint);
                    }
                }
                point.setPointList(childPointEntityList);
            }else{
                point.setPointList(new ArrayList<>());
            }
        }
        if(childPointList!=null && !childPointList.isEmpty()){
            getAllPointList(childPointList);
        }
        return pointList;
    }
}
