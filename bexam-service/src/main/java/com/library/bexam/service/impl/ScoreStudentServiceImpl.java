package com.library.bexam.service.impl;

import com.library.bexam.dao.ScoreClassDao;
import com.library.bexam.dao.ScoreStudentDao;
import com.library.bexam.entity.ScoreStudentEntity;
import com.library.bexam.service.ScoreClassService;
import com.library.bexam.service.ScoreStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JayChen
 */
@Service
public class ScoreStudentServiceImpl implements ScoreStudentService {

    @Autowired
    private ScoreStudentDao scoreStudentDao;

    @Autowired
    private ScoreClassDao scoreClassDao;

    @Override
    public List list(Map<String, Object> params) {
        return scoreStudentDao.list(params);
    }

    @Override
    public Map getRankingListByClassId(String paperId, int classId) {
        int studentCount = scoreStudentDao.getStudentCountByClassId(paperId, classId);
        int firstHalfCount = 0;
        int secondHalfCount = 0;
        if (studentCount % 2 == 0) {
            firstHalfCount = studentCount / 2;
            secondHalfCount = studentCount / 2;
        } else {
            firstHalfCount = ((studentCount - 1) / 2) + 1;
            secondHalfCount = (studentCount - 1) / 2;
        }
        List<ScoreStudentEntity> firstHalfStudents = scoreStudentDao.getFirstHalf(paperId, classId, firstHalfCount);

        getLastRanking(firstHalfStudents);

        List<ScoreStudentEntity> secondHalfStudents = scoreStudentDao.getSecondHalf(paperId, classId, secondHalfCount);

        getLastRanking(secondHalfStudents);

        Map<String, Object> r = new HashMap<>();
        r.put("firstHalf", firstHalfStudents);
        r.put("secondHalf", secondHalfStudents);


        return r;
    }

    /**
     * 获取上一次排名信息
     *
     * @param scoreStudentsInfo 学生成绩信息
     * @return void
     * @author JayChen
     */
    private void getLastRanking(List<ScoreStudentEntity> scoreStudentsInfo) {
        if (scoreStudentsInfo != null) {
            for (ScoreStudentEntity scoreStudentInfo : scoreStudentsInfo) {
                if (scoreStudentInfo != null) {
                    scoreStudentInfo.setLastRanking(scoreStudentDao.getLastRankingByStudentId(scoreStudentInfo.getStudentId(), scoreStudentInfo.getCreateTime()));
                }
            }
        }
    }

    @Override
    public List getClassLevelInfoByPaperId(String paperId) {
        List<Map<String, Object>> classes = scoreClassDao.getClassByPaperId(paperId);
        System.out.println(classes);
        for (Map<String, Object> c : classes) {
            if (c == null) {
                continue;
            }
            Map<String, Object> params = new HashMap<>();
            params.put("paperId", paperId);
            params.put("level", "1");
            params.put("classId", c.get("classId").toString());
            List<Map<String, Object>> level1 = scoreStudentDao.list(params);
            params.put("level", "2");
            List<Map<String, Object>> level2 = scoreStudentDao.list(params);
            params.put("level", "3");
            List<Map<String, Object>> level3 = scoreStudentDao.list(params);
            params.put("level", "4");
            List<Map<String, Object>> level4 = scoreStudentDao.list(params);
            params.put("level", "5");
            List<Map<String, Object>> level5 = scoreStudentDao.list(params);
            c.put("level1", level1);
            c.put("level2", level2);
            c.put("level3", level3);
            c.put("level4", level4);
            c.put("level5", level5);
        }
        return classes;
    }


    @Override
    public List getAttentionStudent(String paperId, int classId) {
        // todo 获取连续三次排名在前五的学生

        return null;
    }
}
