package com.library.bexam.service;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.PaperContentEntity;
import com.library.bexam.entity.PaperEntity;

import java.util.List;
import java.util.Map;

/**
 * @author JayChen
 */
public interface PaperService {
    /**
     * 添加试卷
     *
     * @return boolean
     * @author JayChen
     */
    Result add(List<PaperEntity> paperEntityList);

    /**
     * 获取试卷列表
     *
     * @return List
     * @author JayChen
     */
    List list(Map<String, Object> params);

    /**
     * 根据ID获取试卷信息
     *
     * @return PaperEntity
     * @author JayChen
     */
    PaperEntity get(String paperId);

    /**
     * 修改试卷信息
     *
     * @return boolean
     * @author JayChen
     */
    boolean update(PaperEntity paperEntity);

    /**
     * 更新试卷试题信息
     *
     * @return boolean
     * @author JayChen
     */
    boolean updateContent(PaperEntity paperEntity);

    /**
     * 批量删除试卷
     *
     * @return boolean
     * @author JayChen
     */
    boolean delete(String[] paperIdArray);
}
