package com.library.bexam.dao;

import com.library.bexam.entity.PaperContentEntity;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author JayChen
 */
public interface PaperContentDao {

    /**
     * 添加标题
     *
     * @return boolean
     * @author JayChen
     */
    boolean addTitle(List<PaperContentEntity> titleList);

    /**
     * 添加标题下的试题
     *
     * @return boolean
     * @author JayChen
     */
    @Options(useGeneratedKeys = true, keyProperty = "paperContentId")
    boolean addQuestion(@Param("paperId") String paperId, @Param("parentId") int parentId, @Param("questionList") List<PaperContentEntity> questionList);

    boolean deleteByPaperIds(String[] paperId);

    /**
     * 根据试卷ID获取试卷下的标题
     *
     * @return List
     * @author JayChen
     */
    List<PaperContentEntity> getTitleListByPaperId(String paperId);

    /**
     * 根据标题获取标题下的试题
     *
     * @return List
     * @author JayChen
     */
    List<PaperContentEntity> getQuestionListByTitleId(int titleId);

    /**
     * 修改试题使用次数
     *
     * @param questionList
     * @author caoqian
     * @date 20180102
     */
    boolean updateQuestionUseCount(List<PaperContentEntity> questionList);

    /**
     * 根据试卷ID获取试题
     *
     * @return List
     * @author JayChen
     */
    List<Map<String, Object>> getQuestionByPaperId(String paperId);

    /**
     * 根据内容ID获取内容信息
     *
     * @return String
     * @author JayChen
     */
    PaperContentEntity get(String paperContentId);
}
