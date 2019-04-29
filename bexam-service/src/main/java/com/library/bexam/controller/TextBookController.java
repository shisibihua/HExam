package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.service.TextBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 教材册别处理表现层
 * @author caoqian
 * @date 20181225
 */
@CrossOrigin
@RestController
@RequestMapping("textbook")
public class TextBookController {

    @Autowired
    private TextBookService textBookService;

    /**
     * 根据教材版本id获取教材册别列表
     * @param versionId 教材版本id
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Result searchTextBookList(int versionId){
        return textBookService.searchTextBookList(versionId);
    }
}
