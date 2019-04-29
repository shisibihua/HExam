package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.service.NodeBookService;
import com.library.bexam.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 教材版本处理表现层
 * @author caoqian
 * @date 20181225
 */
@CrossOrigin
@RestController
@RequestMapping("version")
public class VersionController {

    @Autowired
    private VersionService versionService;

    /**
     * 根据科目id获取教材版本列表
     * @param subjectId 科目id
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Result searchVersionList(int subjectId){
        return versionService.searchVersonList(subjectId);
    }
}
