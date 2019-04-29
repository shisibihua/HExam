package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.service.NodeBookService;
import com.library.bexam.util.ConvertResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 教材章节处理表现层
 * @author caoqian
 * @date 20181224
 */
@CrossOrigin
@RestController
@RequestMapping("nodebook")
public class NodeBookController {

    @Autowired
    private NodeBookService nodeBookService;
    /**
     * 获取教材章节列表
     * @return
     */
    @RequestMapping(value = "searchPageList",method = RequestMethod.POST)
    public Result searchPageList(@RequestBody Map<String,Object> params){
        return nodeBookService.serachNodeBookList(params);
    }

    /**
     * 根据教材册别id获取教材章节列表
     * @param textBookId 册别id
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Result searchNodeList(int textBookId){
        return nodeBookService.searchNodeListByBookId(textBookId);
    }
}
