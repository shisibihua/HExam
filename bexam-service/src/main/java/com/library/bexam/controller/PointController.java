package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 知识点处理表现层
 * @author caoqian
 * @date 20181224
 */
@CrossOrigin
@RestController
@RequestMapping("point")
public class PointController {

    @Autowired
    private PointService pointService;

    /**
     * 根据科目id获取知识点列表
     * @param subjectId 科目id
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Result searchPointList(int subjectId){
        return pointService.searchPointListBySubjectId(subjectId);
    }
}
