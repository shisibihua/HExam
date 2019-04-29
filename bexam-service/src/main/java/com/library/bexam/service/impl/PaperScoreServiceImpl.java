package com.library.bexam.service.impl;

import com.library.bexam.dao.PaperContentDao;
import com.library.bexam.dao.PaperScoreDao;
import com.library.bexam.entity.PaperContentEntity;
import com.library.bexam.entity.PaperScoreEntity;
import com.library.bexam.service.PaperScoreService;
import com.library.bexam.service.ScoreClassService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author JayChen
 */
@Service
public class PaperScoreServiceImpl implements PaperScoreService {

    @Autowired
    private PaperScoreDao paperScoreDao;

    @Autowired
    private PaperContentDao paperContentDao;

    @Autowired
    private ScoreClassService scoreClassService;

    @Override
    public boolean add(List<PaperScoreEntity> paperScoreEntityList) {
        return paperScoreDao.add(paperScoreEntityList);
    }

    @Override
    public List list(Map<String, Object> params) {
        return paperScoreDao.list(params);
    }

    @Override
    public boolean delete(String[] paperIds, String[] studentIds) {
        boolean deleteResult = paperScoreDao.delete(paperIds, studentIds);
        return deleteResult;
    }

    /**
     * 获取得分率
     *
     * @return List
     * @author JayChen
     */
    @Override
    public List getScoringRateByClassId(String paperId, String classId) {
        List<Map<String, Object>> questionList = paperContentDao.getQuestionByPaperId(paperId);
        if (questionList != null && !questionList.isEmpty()) {
            for (Map<String, Object> paperContent : questionList) {
                String scoringRate = paperScoreDao.getScoringRateByPaperContentId(paperContent.get("paper_content_id").toString(), classId);
                if (scoringRate != null) {
                    paperContent.put("scoringRate", Float.parseFloat(scoringRate));
                } else {
                    paperContent.put("scoringRate", "");
                }
            }
        }
        return questionList;
    }

    /**
     * 获取试题错误名单，并用班级分组
     *
     * @return List
     * @author JayChen
     */
    @Override
    public List getWrongStudent(String paperContentId) {
        PaperContentEntity paperContent = paperContentDao.get(paperContentId);
        List<Map<String, Object>> classes = new ArrayList<>();
        if (paperContent != null) {
            classes = scoreClassService.getClassByPaperId(paperContent.getPaperId());
            if (classes != null && !classes.isEmpty()) {
                for (Map<String, Object> c : classes) {
                    if (c == null) {
                        continue;
                    }
                    c.put("students", paperScoreDao.getWrongStudent(c.get("classId").toString(), paperContentId));
                    String wrongRate = paperScoreDao.getWrongRate(c.get("classId").toString(), paperContentId);
                    c.put("wrongRate", wrongRate == null ? 0 : Float.parseFloat(wrongRate));
                }
            }
        }
        return classes;
    }

    /**
     * 获取班级错误率
     *
     * @return List
     * @author JayChen
     */
    @Override
    public List getWrongRate(String paperContentId) {
        PaperContentEntity paperContent = paperContentDao.get(paperContentId);
        List<Map<String, Object>> classes = new ArrayList<>();
        if (paperContent != null) {
            classes = scoreClassService.getClassByPaperId(paperContent.getPaperId());
            if (classes != null && !classes.isEmpty()) {
                for (Map<String, Object> c : classes) {
                    if (c == null) {
                        continue;
                    }
                    String wrongRate = paperScoreDao.getWrongRate(c.get("classId").toString(), paperContentId);
                    c.put("wrongRate", wrongRate == null ? 0 : Float.parseFloat(wrongRate));
                }
            }
        }
        return classes;
    }

    @Override
    public List getScoringRate(String paperId) {
        List<Map<String, Object>> classes = scoreClassService.getClassByPaperId(paperId);

        List<Map<String, Object>> questionList = paperContentDao.getQuestionByPaperId(paperId);

        if (classes != null && questionList != null) {
            for (Map<String, Object> c : classes) {
                if (c == null) {
                    continue;
                }

                for (Map<String, Object> paperContent : questionList) {
                    if (paperContent == null) {
                        continue;
                    }
                    System.out.println(paperContent);
                    String scoringRate = paperScoreDao.getScoringRateByPaperContentId(paperContent.get("paperContentId").toString(), c.get("classId").toString());
                    if (scoringRate != null) {
                        paperContent.put("scoringRate", Float.parseFloat(scoringRate));
                    } else {
                        paperContent.put("scoringRate", "");
                    }
                }
                c.put("questions", questionList);
            }
        }
        return classes;
    }

}
