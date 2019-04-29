package com.library.bexam.dao;

import com.library.bexam.entity.QuestionDifficultEntity;

import java.util.List;

/**
 * 试题难易度数据访问对象
 *
 * @author JayChen
 */
public interface QuestionDifficultDao {
    /**
     * 获取试题难易度列表
     *
     * @return
     */
    List list();

    /**
     * 根据ID获取难易度信息
     *
     * @return QuestionDifficultEntity
     * @author JayChen
     */
    QuestionDifficultEntity getQuestionDifficultById(String questionDifficultById);

    /**
     * 添加试题难易度
     *
     * @param questionDifficultEntity
     * @return
     */
    boolean add(QuestionDifficultEntity questionDifficultEntity);

    /**
     * 删除试题难易度
     *
     * @param difficultIdArray
     * @return
     */
    boolean delete(String[] difficultIdArray);

    /**
     * 更新试题难易度信息
     *
     * @param questionDifficultEntity
     * @return
     */
    boolean update(QuestionDifficultEntity questionDifficultEntity);
}
