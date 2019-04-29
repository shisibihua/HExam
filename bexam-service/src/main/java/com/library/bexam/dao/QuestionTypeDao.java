package com.library.bexam.dao;

import com.library.bexam.entity.QuestionTypeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 题型数据访问对象
 *
 * @author JayChen
 */
public interface QuestionTypeDao {

    /**
     * 获取题型列表
     *
     * @return List
     * @author JayChen
     */
    List list(@Param("subjectId")int subjectId);

    /**
     * 添加题型
     *
     * @return boolean
     * @author JayChen
     */
    boolean add(QuestionTypeEntity questionTypeEntity);

    /**
     * 根据ID获取题型信息
     *
     * @return Result
     * @author JayChen
     */
    QuestionTypeEntity getQuestionTypeById(String typeId);

    /**
     * 批量添加题型
     *
     * @return boolean
     * @author JayChen
     */
    boolean batchAdd(List<QuestionTypeEntity> questionTypeEntities);

    /**
     * 从学科网拉取试题类型，存类型id
     * @param questionTypeEntities
     * @return
     */
    boolean batchAddForXK(List<QuestionTypeEntity> questionTypeEntities);

    /**
     * 添加学科与题型关联关系
     *
     * @return boolean
     * @author JayChen
     */
    boolean addSubject2QuestionType(@Param("subjectId") String subjectId, @Param("questionTypeId") String questionTypeId);

    /**
     * 批量添加学科与题型关联关系
     *
     * @return boolean
     * @author JayChen
     */
    boolean batchAddSubject2QuestionType(List<QuestionTypeEntity> questionTypeEntities);

    /**
     * 删除题型
     *
     * @return boolean
     * @author JayChen
     */
    boolean delete(String[] typeIdArray);

    /**
     * 删除学科与题型关联关系
     *
     * @return boolean
     * @author JayChen
     */
    boolean deleteSubject2QuestionType(String[] typeIdArray);

    /**
     * 更新题型信息
     *
     * @return boolean
     * @author JayChen
     */
    boolean update(QuestionTypeEntity questionTypeEntity);
}
