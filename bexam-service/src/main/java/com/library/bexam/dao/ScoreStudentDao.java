package com.library.bexam.dao;

import com.library.bexam.entity.ScoreStudentEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author JayChen
 */
public interface ScoreStudentDao {

    List list(Map<String, Object> params);

    int getStudentCountByClassId(@Param("paperId") String paperId, @Param("classId") int classId);

    List<ScoreStudentEntity> getFirstHalf(@Param("paperId") String paperId, @Param("classId") int classId, @Param("firstHalfCount") int firstHalfCount);

    List<ScoreStudentEntity> getSecondHalf(@Param("paperId") String paperId, @Param("classId") int classId, @Param("secondHalfCount") int secondHalfCount);

    Integer getLastRankingByStudentId(@Param("studentId") int studentId, @Param("dateTime") String dateTime);
}
