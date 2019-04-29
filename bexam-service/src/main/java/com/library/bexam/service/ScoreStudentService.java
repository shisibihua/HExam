package com.library.bexam.service;

import java.util.List;
import java.util.Map;

/**
 * @author JayChen
 */
public interface ScoreStudentService {

    List list(Map<String, Object> params);

    Map getRankingListByClassId(String paperId, int classId);

    List getClassLevelInfoByPaperId(String paperId);

    List getAttentionStudent(String paperId, int classId);

}
