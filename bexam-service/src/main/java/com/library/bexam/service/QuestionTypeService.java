package com.library.bexam.service;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.QuestionTypeEntity;

import java.util.List;

/**
 * 题型服务接口
 *
 * @author JayChen
 */
public interface QuestionTypeService {

    /**
     * 获取题型列表
     *
     * @return List
     * @author JayChen
     */
    List list(int subjectId);

    /**
     * 根据ID获取题型信息
     *
     * @return Result
     * @author JayChen
     */
    QuestionTypeEntity getQuestionTypeById(String typeId);

    /**
     * 添加题型
     *
     * @return boolean
     * @author JayChen
     */
    boolean add(QuestionTypeEntity questionTypeEntity);

    /**
     * 批量添加题型
     * @param questionTypeEntities
     * @param isAddTypeId  批量保存是否保存试题id，true:保存，false:不保存
     * @return boolean
     * @author JayChen
     */
    boolean batchAdd(List<QuestionTypeEntity> questionTypeEntities,boolean isAddTypeId);

    /**
     * 删除题型
     *
     * @return boolean
     * @author JayChen
     */
    boolean delete(String[] typeIdArray);


    /**
     * 更新题型信息
     *
     * @return boolean
     * @author JayChen
     */
    Result update(QuestionTypeEntity questionTypeEntity);
}
