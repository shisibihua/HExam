package com.library.bexam.service;

import com.library.bexam.entity.PaperScoreEntity;

import java.util.List;
import java.util.Map;

/**
 * @author JayChen
 */
public interface PaperScoreService {

    boolean add(List<PaperScoreEntity> paperScoreEntityList);

    List list(Map<String, Object> params);

    boolean delete(String[] paperIds, String[] studentIds);

    List getScoringRateByClassId(String paperId, String classId);

    List getWrongStudent(String paperContentId);

    List getWrongRate(String paperContentId);

    List getScoringRate(String paperId);
}
