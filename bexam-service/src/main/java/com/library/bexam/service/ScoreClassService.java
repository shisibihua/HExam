package com.library.bexam.service;

import java.util.List;
import java.util.Map;

/**
 * @author JayChen
 */
public interface ScoreClassService {

    List<Map<String, Object>> getClassByPaperId(String paperId);

    List list(String paperId);

    Map getPaperScoreInfo(String paperId);
}
