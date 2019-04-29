package com.library.bexam.service.impl;

import com.library.bexam.dao.ScoreClassDao;
import com.library.bexam.service.ScoreClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author JayChen
 */
@Service
public class ScoreClassServiceImpl implements ScoreClassService {

    @Autowired
    private ScoreClassDao scoreClassDao;

    @Override
    public List<Map<String, Object>> getClassByPaperId(String paperId) {
        return scoreClassDao.getClassByPaperId(paperId);
    }

    @Override
    public List list(String paperId) {
        return scoreClassDao.list(paperId);
    }

    @Override
    public Map getPaperScoreInfo(String paperId) {
        return scoreClassDao.getPaperScoreInfo(paperId);
    }
}
