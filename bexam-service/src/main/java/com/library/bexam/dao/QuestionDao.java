package com.library.bexam.dao;

import com.library.bexam.entity.QuestionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 试题数据库访问对象
 *
 * @author JayChen
 */
public interface QuestionDao {

    /**
     * 添加试题
     *
     * @return boolean
     * @author JayChen
     */
    boolean batchAdd(List<QuestionEntity> questionEntityList);

    /**
     * 试题列表
     *
     * @return List
     * @author JayChen
     */
    List list(Map<String, Object> params);

    /**
     * 获取试题总数
     * @param params
     * @author caoqian
     * @date 20180105
     * @return
     */
    int getTotalCount(Map<String, Object> params);

    /**
     * 根据ID获取试题信息
     *
     * @return QuestionEntity
     * @author JayChen
     */
    QuestionEntity getQuestionById(String questionId);

    /**
     * 修改试题
     *
     * @return boolean
     * @author JayChen
     */
    boolean update(QuestionEntity questionEntity);

    /**
     * 删除试题
     *
     * @return boolean
     * @author JayChen
     */
    boolean delete(@Param("questionIdArray") String[] questionIdArray,@Param("userId") int userId);

    /**
     * 保存学科网试题答案与解析
     *
     * @param question
     * @return
     */
    boolean addQuestionAnswerAndAnalyze(QuestionEntity question);

    /**
     * 根据科目id查询试题信息
     *
     * @param subjectId  科目id
     * @param firstIndex 起始页
     * @param pageSize   每页大小
     * @return
     * @author caoqian
     * @date 20181227
     */
    List<QuestionEntity> searchQuestionListBySubId(@Param("subjectId") int subjectId, @Param("firstIndex") int firstIndex,
                                                   @Param("pageSize") int pageSize);

    /**
     * 根据试题ID获取试题答案和解析
     *
     * @param questionId 试题ID
     * @return Result
     * @author JayChen
     */
    Map<String, Object> getAnswerAndAnalysis(String questionId);

    /**
     * 收藏试题/加入试题篮
     * @param params      用户id、试题id、存储类型，0：收藏；1：试题篮
     * @author caoqian
     * @return
     */
    boolean collectQuestions(List<Map<String, Object>> params);

    /**
     * 查询老师收藏的试题/试题篮中试题列表
     * @param userId 用户id
     * @param type 0：收藏；1：试题篮
     * @author caoqian
     * @date 20181230
     * @return
     */
    List<QuestionEntity> searchQuestionsByUId(@Param("userId") int userId,@Param("type")int type);

    /**
     * 查询试题篮中试题信息
     * @param userId 用户id
     * @param type 1：试题篮
     * @author caoqian
     * @date 20190105
     * @return
     */
    List<Map<String,Object>> searchQuestionsBasket(@Param("userId") int userId,@Param("type")int type);
    /**
     * 拉取学科试题
     * @param questionEntity
     * @return
     */
    boolean add(QuestionEntity questionEntity);

    /**
     * 删除收藏的试题
     * @param userId
     * @param questionId
     * @param type  存储类型，0：收藏；1：试题篮
     * @return
     */
    boolean delCollectQuestions(@Param("userId") int userId,@Param("questionId") int questionId,@Param("type")int type);

    /**
     * 查询所有收藏的/加入试题篮的试题
     * @param userId
     * @return
     */
    List<Map<String,Object>> searchAllCollectQuestions(int userId);

    /**
     * 根据用户id，试题id、类型type验证该试题是否已被收藏
     * @param params  userId、questionId、type，0：收藏；1：试题篮
     * @return
     */
    int exitsCollectQuestions(Map<String, Object> params);

    /**
     * 根据用户id和题型id删除试题篮
     * @param params
     * @author caoqian
     * @return
     */
    boolean delQuestionsBasketByTypeId(Map<String, Object> params);

    /**
     * 添加试题
     * @param questionEntityList
     * @author caoqian
     * @date 20190108
     * @return
     */
    boolean addUser2Questions(List<QuestionEntity> questionEntityList);

    /**
     * 删除试题与用户的关系
     * @param questionIdArr 试题ids,多个","分割
     * @author caoqian
     * @date 20190108
     * @return
     */
    boolean delUser2Question(@Param("questionIdsArr") String[] questionIdArr,@Param("userId") int userId);

    /**
     * 修改试题是否公开
     * @param params  {"userId":1,"questionId":1,"isPublic"：0}
     * @author caoqian
     * @return
     */
    boolean changeQuestionPublic(Map<String, Object> params);

    /**
     * 查询用户上传的试题
     * @param params
     * @return
     */
    List<QuestionEntity> searchQuestionsByUIdAndPId(Map<String, Object> params);

    /**
     * 查询用户上传的试题数量
     * @param params
     * @return
     */
    int searchUploadQuestionsCount(Map<String, Object> params);

    /**
     * 查询学科网试题及用户公开的试题
     * @param params
     * @return
     */
    List<QuestionEntity> searchPublicQuestions(Map<String, Object> params);

    /**
     * 查询学科网试题及用户公开的试题数量
     * @param params
     * @return
     */
    int searchPublicQuestionsCount(Map<String, Object> params);
}
