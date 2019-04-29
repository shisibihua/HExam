package com.library.bexam.service;

import com.library.bexam.common.pojo.model.Result;

/**
 * 教材册别业务处理
 * @author caoqian
 * @date 20181225
 */
public interface TextBookService {
    /**
     * 根据教材版本id获取教材册别列表
     * @param versionId 教材版本id
     * @return
     */
    Result searchTextBookList(int versionId);
}
