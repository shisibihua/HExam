package com.library.bexam.service;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.NodeBookEntity;
import com.library.bexam.form.NodeBookForm;

import java.util.List;
import java.util.Map;

/**
 * 处理教材章节
 * @author caoqian
 * @date 20181224
 */
public interface NodeBookService {
    /**
     * 查询章节列表
     * @param params
     * @return
     */
    Result serachNodeBookList(Map<String,Object> params);

    /**
     * 根据教材册别id获取教材章节列表
     * @param textBookId 册别id
     * @return
     */
    Result searchNodeListByBookId(int textBookId);

    /**
     * 递归获取所有章节
     * @param nodeBookList
     * @return
     */
    List<NodeBookEntity> getAllNodeBook(List<NodeBookEntity> nodeBookList);
}
