package com.library.bexam.service;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.QuestionDifficultEntity;

import java.util.List;

/**
 * 试题难易度服务接口
 *
 * @author JayChen
 */
public interface QuestionDifficultService {

    /**
     * 获取试题难易度列表
     *
     * @return List
     * @author JayChen
     */
    List list();

    /**
     * 根据ID获取难易度信息
     *
     * @return QuestionDifficultEntity
     * @author JayChen
     */
    QuestionDifficultEntity getQuestionDifficultById(String questionDifficultId);

    /**
     * 添加试题难易度
     *
     * @return boolean
     * @author JayChen
     */
    boolean add(QuestionDifficultEntity questionDifficultEntity);

    /**
     * 删除试题难易度
     *
     * @return boolean
     * @author JayChen
     */
    boolean delete(String[] difficultIdArray);

    /**
     * 更新试题难易度
     *
     * @return boolean
     * @author JayChen
     */
    boolean update(QuestionDifficultEntity questionDifficultEntity);

}
