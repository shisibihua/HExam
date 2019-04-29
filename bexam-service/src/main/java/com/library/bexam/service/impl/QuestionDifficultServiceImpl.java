package com.library.bexam.service.impl;

import com.library.bexam.dao.QuestionDifficultDao;
import com.library.bexam.entity.QuestionDifficultEntity;
import com.library.bexam.service.QuestionDifficultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 试题难易度实现类
 *
 * @author JayChen
 */
@Service
public class QuestionDifficultServiceImpl implements QuestionDifficultService {

    @Autowired
    private QuestionDifficultDao questionDifficultDao;

    /**
     * 获取难易度列表
     *
     * @return List
     * @author JayChen
     */
    @Override
    public List list() {
        return questionDifficultDao.list();
    }

    /**
     * 根据ID获取难易度信息
     *
     * @return QuestionDifficultEntity
     * @author JayChen
     */
    @Override
    public QuestionDifficultEntity getQuestionDifficultById(String questionDifficultId) {
        return questionDifficultDao.getQuestionDifficultById(questionDifficultId);
    }

    /**
     * 添加难易度
     *
     * @return boolean
     * @author JayChen
     */
    @Override
    public boolean add(QuestionDifficultEntity questionDifficultEntity) {
        return questionDifficultDao.add(questionDifficultEntity);
    }

    /**
     * 删除难易度
     *
     * @return boolean
     * @author JayChen
     */
    @Override
    public boolean delete(String[] difficultIdArray) {
        return questionDifficultDao.delete(difficultIdArray);
    }

    /**
     * 修改难易度
     *
     * @return boolean
     * @author JayChen
     */
    @Override
    public boolean update(QuestionDifficultEntity questionDifficultEntity) {
        return questionDifficultDao.update(questionDifficultEntity);
    }
}
