package com.library.bexam.service;

import com.library.bexam.entity.PaperTypeEntity;
import com.library.bexam.entity.QuestionTypeEntity;

import java.util.List;

/**
 * 试卷类型服务接口
 *
 * @author JayChen
 */
public interface PaperTypeService {

    /**
     * 获取试卷类型列表
     *
     * @author JayChen
     * @return List
     */
    List list();

    /**
     * 添加试卷类型
     * 
     * @author JayChen
     * @return boolean
     */
    boolean add(PaperTypeEntity paperTypeEntity);

    /**
     * 删除试卷类型
     *
     * @author JayChen
     * @return boolean
     */
    boolean delete(String[] typeIdArray);

    /**
     * 更新试卷类型信息
     *
     * @author JayChen
     * @return boolean
     */
    boolean update(PaperTypeEntity paperTypeEntity);

    PaperTypeEntity get(int typeId);
}
