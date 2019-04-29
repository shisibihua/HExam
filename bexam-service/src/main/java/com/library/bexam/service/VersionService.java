package com.library.bexam.service;

import com.library.bexam.common.pojo.model.Result;

/**
 * 教材版本业务处理
 * @author caoqian
 * @date 20181225
 */
public interface VersionService {
    /**
     * 根据科目id查询教材版本
     * @param subjectId
     * @return
     */
    Result searchVersonList(int subjectId);
}
