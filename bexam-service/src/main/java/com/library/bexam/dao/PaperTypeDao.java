package com.library.bexam.dao;

import com.library.bexam.entity.PaperTypeEntity;

import java.util.List;

/**
 * 试卷类型数据访问对象
 *
 * @author JayChen
 */
public interface PaperTypeDao {

    /**
     * 获取试卷类型列表
     *
     * @return
     */
    List list();

    /**
     * 添加试卷类型
     *
     * @param paperTypeEntity
     * @return
     */
    boolean add(PaperTypeEntity paperTypeEntity);

    /**
     * 删除试卷类型
     *
     * @param typeIdArray
     * @return
     */
    boolean delete(String[] typeIdArray);

    /**
     * 更新试卷类型
     *
     * @param paperTypeEntity
     * @return
     */
    boolean update(PaperTypeEntity paperTypeEntity);

    PaperTypeEntity get(int typeId);
}
