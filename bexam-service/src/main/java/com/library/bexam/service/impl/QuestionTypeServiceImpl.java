package com.library.bexam.service.impl;


import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.dao.QuestionTypeDao;
import com.library.bexam.entity.QuestionTypeEntity;
import com.library.bexam.service.QuestionTypeService;
import com.library.bexam.util.ConvertResult;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.alicp.jetcache.Cache.logger;

/**
 * 题型实现类
 *
 * @author JayChen
 */
@Service
public class QuestionTypeServiceImpl implements QuestionTypeService {

    @Autowired
    private QuestionTypeDao questionTypeDao;

    /**
     * 获取题型列表
     *
     * @return List
     * @author JayChen
     */
    @Override
    public List list(int subjectId) {
        List list = questionTypeDao.list(subjectId);

        return list;
    }

    /**
     * 根据ID获取题型信息
     *
     * @return Result
     * @author JayChen
     */
    @Override
    public QuestionTypeEntity getQuestionTypeById(String typeId) {
        return questionTypeDao.getQuestionTypeById(typeId);
    }

    /**
     * 添加题型
     *
     * @return boolean
     * @author JayChen
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean add(QuestionTypeEntity questionTypeEntity) {
        String subjectId=questionTypeEntity.getSubjectId();
        if(StringUtil.isEmpty(subjectId)){
            return false;
        }
        boolean addResult = false;
        try {
            addResult = questionTypeDao.add(questionTypeEntity);
        } catch (Exception e) {
            logger.error("添加试题类型异常",e);
        }
        if (addResult && questionTypeEntity.getTypeId() != null) {
            addResult = questionTypeDao.addSubject2QuestionType(subjectId, questionTypeEntity.getTypeId());
        }

        return addResult;
    }

    /**
     * 批量添加题型
     *
     * @return boolean
     * @author JayChen
     */
    @Override
    public boolean batchAdd(List<QuestionTypeEntity> questionTypeEntities,boolean isAddTypeId) {
        boolean addResult = false;
        try {
            if(isAddTypeId){
                //从学科网拉取数据
                addResult = questionTypeDao.batchAddForXK(questionTypeEntities);
            }else {
                addResult = questionTypeDao.batchAdd(questionTypeEntities);
            }
        } catch (Exception e) {
            logger.error("保存试题类型异常",e);
        }
        if (addResult) {
            addResult = questionTypeDao.batchAddSubject2QuestionType(questionTypeEntities);
        }

        return addResult;
    }

    /**
     * 删除题型
     *
     * @return boolean
     * @author JayChen
     */
    @Override
    public boolean delete(String[] typeIdArray) {
        boolean deleteResult = false;
        try {
            deleteResult = questionTypeDao.delete(typeIdArray);
            if (deleteResult) {
                deleteResult = questionTypeDao.deleteSubject2QuestionType(typeIdArray);
            }
        } catch (Exception e) {
            logger.error("删除试题类型异常",e);
        }

        return deleteResult;
    }

    /**
     * 修改题型信息
     *
     * @return boolean
     * @author JayChen
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result update(QuestionTypeEntity questionTypeEntity) {
        if(questionTypeEntity==null || StringUtil.isEmpty(questionTypeEntity.getSubjectId()) ||
                StringUtil.isEmpty(questionTypeEntity.getTypeId()) ||
                StringUtil.isEmpty(questionTypeEntity.getTypeName())){
               return ConvertResult.getParamErrorResult("学科id或试题id或试题名称不能为空");
        }
        boolean updateResult=false;
        try {
            //更新试题信息
            updateResult = questionTypeDao.update(questionTypeEntity);
            if(updateResult) {
                //修改试题与学科的关系
                questionTypeDao.deleteSubject2QuestionType(new String[]{questionTypeEntity.getTypeId()});
                questionTypeDao.addSubject2QuestionType(questionTypeEntity.getSubjectId(), questionTypeEntity.getTypeId());
            }
        }catch (Exception e){
            logger.error("修改试题类型异常，typeId="+questionTypeEntity.getTypeId()+
                    ",subjectId="+questionTypeEntity.getSubjectId(),e);
        }
        return ConvertResult.getSuccessResult(updateResult);
    }
}
