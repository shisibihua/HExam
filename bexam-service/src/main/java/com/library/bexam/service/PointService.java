package com.library.bexam.service;

import com.library.bexam.common.pojo.model.Result;

import java.util.Map;

/**
 * 处理知识点
 * @author caoqian
 * @date 20181224
 */
public interface PointService {

    /**
     * 根据科目id获取知识点列表
     * @param subjectId 科目id
     * @return
     */
    Result searchPointListBySubjectId(int subjectId);
}
