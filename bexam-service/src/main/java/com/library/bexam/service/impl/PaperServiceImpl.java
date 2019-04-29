package com.library.bexam.service.impl;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.dao.PaperContentDao;
import com.library.bexam.dao.PaperDao;
import com.library.bexam.entity.PaperContentEntity;
import com.library.bexam.entity.PaperEntity;
import com.library.bexam.entity.QuestionEntity;
import com.library.bexam.service.PaperService;
import com.library.bexam.util.ConvertResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author JayChen
 */
@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperDao paperDao;

    @Autowired
    private PaperContentDao paperContentDao;

    /**
     * 添加试卷
     *
     * @return boolean
     * @author JayChen
     */
    @Override
    public Result add(List<PaperEntity> paperEntityList) {
        if(paperEntityList==null || paperEntityList.isEmpty()){
            return ConvertResult.getParamErrorResult("试题信息不能为空");
        }
        boolean addResult = paperDao.add(paperEntityList);
        if (addResult) {
            for (PaperEntity paperEntity : paperEntityList) {
                // 先添加标题
                List<PaperContentEntity> titleList = paperEntity.getPaperContent();
                for (PaperContentEntity paperContentEntity : titleList) {
                    paperContentEntity.setPaperId(paperEntity.getPaperId());
                }
                addResult = paperContentDao.addTitle(titleList);
                if (addResult) {
                    for (PaperContentEntity paperContentEntity : titleList) {
                        Set<String> questionIdSet=new HashSet<>();
                        List<PaperContentEntity> list=new ArrayList<>();
                        List<PaperContentEntity> questionList = paperContentEntity.getQuestions();
                        if(questionList!=null && !questionList.isEmpty()){
                            for(PaperContentEntity paperContent:questionList){
                                if(!questionIdSet.contains(paperContent.getQuestionId())){
                                    list.add(paperContent);
                                    questionIdSet.add(paperContent.getQuestionId());
                                }
                            }
                            paperContentDao.addQuestion(paperEntity.getPaperId(), paperContentEntity.getPaperContentId(), list);
                            //修改试题使用次数
                            paperContentDao.updateQuestionUseCount(list);
                        }
                    }
                }
            }
        }
        return ConvertResult.getSuccessResult(addResult);
    }

    /**
     * 获取试卷列表
     *
     * @return List
     * @author JayChen
     */
    @Override
    public List list(Map<String, Object> params) {
        return paperDao.list(params);
    }

    /**
     * 根据ID获取试卷信息
     *
     * @return PaperEntity
     * @author JayChen
     */
    @Override
    public PaperEntity get(String paperId) {
        PaperEntity paperEntity = paperDao.get(paperId);
        List<PaperContentEntity> titleList = null;
        if (paperEntity != null) {
            titleList = paperContentDao.getTitleListByPaperId(paperId);
            if (titleList != null) {
                for (PaperContentEntity paperContentEntity : titleList) {
                    paperContentEntity.setQuestions(paperContentDao.getQuestionListByTitleId(paperContentEntity.getPaperContentId()));
                }
            }

            paperEntity.setPaperContent(titleList);
        }

        return paperEntity;
    }

    /**
     * 修改试卷
     *
     * @return boolean
     * @author JayChen
     */
    @Override
    public boolean update(PaperEntity paperEntity) {
        return paperDao.update(paperEntity);
    }

    @Override
    public boolean updateContent(PaperEntity paperEntity) {
        return false;
    }

    /**
     * 批量删除试卷
     *
     * @return boolean
     * @author JayChen
     */
    @Override
    public boolean delete(String[] paperIdArray) {
        boolean deleteResult = paperDao.delete(paperIdArray);
        if (deleteResult) {
            paperContentDao.deleteByPaperIds(paperIdArray);
        }
        return deleteResult;
    }
}
