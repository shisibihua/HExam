package com.library.bexam.dao;

import java.util.List;
import java.util.Map;

/**
 * @author JayChen
 */
public interface ScoreClassDao {

    List<Map<String, Object>> getClassByPaperId(String paperId);

    List list(String paperId);

    Map getPaperScoreInfo(String paperId);
}
