package com.library.bexam.dao;

import com.library.bexam.entity.PaperEntity;

import java.util.List;
import java.util.Map;

/**
 * @author JayChen
 */
public interface PaperDao {
    /**
     * 添加试卷
     *
     * @return boolean
     * @author JayChen
     */
    boolean add(List<PaperEntity> paperEntityList);

    /**
     * 试卷列表
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
     * 修改试卷
     *
     * @return boolean
     * @author JayChen
     */
    boolean update(PaperEntity paperEntity);

    /**
     * 删除试卷
     *
     * @return boolean
     * @author JayChen
     */
    boolean delete(String[] paperIdArray);
}
