package com.library.bexam.service;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.QuestionEntity;

import java.util.List;
import java.util.Map;

/**
 * 试题接口
 *
 * @author JayChen
 */
public interface QuestionService {

    /**
     * 批量添加试题
     *
     * @return boolean
     * @author JayChen
     */
    Result batchAdd(List<QuestionEntity> questionEntityList);
    boolean add(QuestionEntity questionEntity);

    /**
     * 获取试题列表
     *
     * @return List
     * @author JayChen
     */
    List list(Map<String, Object> params);

    int getTotalCount(Map<String, Object> params);

    /**
     * 根据ID获取试题信息
     *
     * @return QuestionEntity
     * @author JayChen
     */
    QuestionEntity getQuestionById(String questionId);

    /**
     * 修改试题信息
     *
     * @return boolean
     * @author JayChen
     */
    boolean update(QuestionEntity questionEntity);

    /**
     * 批量删除试题
     *
     * @return boolean
     * @author JayChen
     */
    Result delete(Map<String, Object> params);

    /**
     * 保存试题答案与解析
     *
     * @param questionEntityList
     * @author caoqian
     * @return
     */
    boolean addQuestionAnswerAndAnalyze(List<QuestionEntity> questionEntityList);

    /**
     * 根据科目查询试题列表
     *
     * @param subjectId
     * @author caoqian
     * @return
     */
    List<QuestionEntity> searchQuestionListBySubId(int subjectId, int page, int pageSize);

    /**
     * 根据试题ID获取试题答案和解析
     *
     * @author JayChen
     * @return QuestionEntity
     */
    Map<String, Object> getAnswerAndAnalysis(String questionId);

    /**
     * 收藏试题
     * @author caoqian
     * @param params
     * @return
     */
    Result collectQuestions(Map<String, Object> params);

    /**
     * 加入试题篮
     * @author caoqian
     * @param params
     * @return
     */
    Result collectQuestionsBasket(List<Map<String, Object>> params);

    /**
     * 查询老师收藏的试题/试题篮中试题列表
     * @param userId 用户id
     * @param type 0：收藏；1：试题篮
     * @author caoqian
     * @date 20181230
     * @return
     */
    Result searchQuestionsByUId(int userId,int type);

    /**
     * 删除老师收藏的试题/试题篮
     * @author caoqian
     * @return
     */
    Result delCollectQuestions(Map<String,Object> params);

    /**
     * 查询试题篮信息
     * @param userId  用户id
     * @param type    1
     * @return
     */
    Result searchQuestionsBasket(int userId, int type);

    /**
     * 按题型删除试题篮
     * @author caoqian
     * @return
     */
    Result delQuestionsBasketByTypeId(Map<String, Object> params);


    /**
     * 查询试题篮试题详情
     * @param userId 用户id
     * @param type  1
     * @author caoqian
     * @date 20181230
     * @return
     */
    Result searchQuestionBasketDetails(int userId, int type);

    /**
     * 修改试题是否公开
     * @param params  {"userId":1,"questionId":1,"isPublic"：0}
     * @author caoqian
     * @return
     */
    Result changeQuestionPublic(Map<String, Object> params);

    /**
     * 根据知识点获取用户上传的试题
     * @param userId       用户id
     * @param isPublic     是否公开，0:不公开,1:公开
     * @param pointId      一级知识点
     * @param pointId2     二级知识点
     * @param pointId3     三级知识点
     * @param page         当前页
     * @param pageSize     每页条数
     * @return
     */
    Result getQuestionByUIdAndPId(int userId, String isPublic, int pointId, int pointId2,
                                  int pointId3,int page,int pageSize);
    /**
     * 根据知识点获取学科网试题及所有用户上传的试题
     * @param pointId      一级知识点
     * @param pointId2     二级知识点
     * @param pointId3     三级知识点
     * @param page         当前页
     * @param pageSize     每页条数
     * @return
     */
    Result searchPublicQuestions(int pointId, int pointId2, int pointId3, int page, int pageSize);
}
