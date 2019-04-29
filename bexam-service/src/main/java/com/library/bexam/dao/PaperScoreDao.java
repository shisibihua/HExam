package com.library.bexam.dao;

import com.library.bexam.entity.PaperScoreEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author JayChen
 */
public interface PaperScoreDao {

    boolean add(List<PaperScoreEntity> paperScoreEntityList);

    List list(Map<String, Object> params);

    boolean delete(@Param("paperIds") String[] paperIds, @Param("studentIds") String[] studentIds);

    /**
     * 获取试题的得分率
     *
     * @return String
     * @author JayChen
     */
    String getScoringRateByPaperContentId(@Param("paperContentId") String paperContentId, @Param("classId") String classId);

    List getWrongStudent(@Param("classId") String classId, @Param("paperContentId") String paperContentId);

    String getWrongRate(@Param("classId") String classId, @Param("paperContentId") String paperContentId);
}
