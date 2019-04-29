package com.library.bexam.service.impl;

import com.library.bexam.common.pojo.model.Page;
import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.dao.QuestionDao;
import com.library.bexam.entity.QuestionEntity;
import com.library.bexam.service.QuestionService;
import com.library.bexam.util.ConvertResult;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 试题服务实现类
 *
 * @author JayChen
 */
import static com.alicp.jetcache.Cache.logger;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    /**
     * 添加试题
     *
     * @return boolean
     * @author JayChen
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result batchAdd(List<QuestionEntity> questionEntityList) {
        if(questionEntityList==null || questionEntityList.isEmpty()){
            return ConvertResult.getParamErrorResult("试题不能为为空");
        }
        boolean addResult=questionDao.batchAdd(questionEntityList);
        if(addResult){
            questionDao.addUser2Questions(questionEntityList);
        }
        return ConvertResult.getSuccessResult(addResult);
    }
    @Override
    public boolean add(QuestionEntity questionEntity) {
        return questionDao.add(questionEntity);
    }
    /**
     * 获取试题列表
     *
     * @return List
     * @author JayChen
     */
    @Override
    public List list(Map<String, Object> params) {
        List<QuestionEntity> questionList=questionDao.list(params);
        if(questionList!=null && !questionList.isEmpty()){
            if(params.containsKey("userId")) {
                int userId = (int) params.get("userId");
                List<Map<String, Object>> allCollectionQuestionList = questionDao.searchAllCollectQuestions(userId);
                for (QuestionEntity question : questionList) {
                    if (allCollectionQuestionList != null && !allCollectionQuestionList.isEmpty()) {
                        for (Map<String, Object> collectQuestion : allCollectionQuestionList) {
                            if (String.valueOf(question.getQuestionId()).equals(collectQuestion.get("question_id").toString())) {
                                //该试题已被收藏
                                if("0".equals(collectQuestion.get("type"))) {
                                    question.setIsCollection(true);
                                }
                                if("1".equals(collectQuestion.get("type"))){
                                    question.setIsAddBasket(true);
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }else{
                for (QuestionEntity question : questionList) {
                    question.setIsCollection(false);
                    question.setIsAddBasket(false);
                }
            }
        }
        return questionList;
    }

    @Override
    public int getTotalCount(Map<String, Object> params) {
        return questionDao.getTotalCount(params);
    }
    /**
     * 根据ID获取试题信息
     *
     * @author JayChen
     * @return QuestionEntity
     */
    @Override
    public QuestionEntity getQuestionById(String questionId) {
        return questionDao.getQuestionById(questionId);
    }

    /**
     * 修改试题
     *
     * @return boolean
     * @author JayChen
     */
    @Override
    public boolean update(QuestionEntity questionEntity) {
        return questionDao.update(questionEntity);
    }

    /**
     * 批量删除试题
     *
     * @return boolean
     * @author JayChen
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result delete(Map<String, Object> params) {

        if (params==null || params.isEmpty() || !params.containsKey("questionIds") || !params.containsKey("userId")) {
            return ConvertResult.getParamErrorResult("试题ids或用户id不能为空");
        }
        String[] questionIdArray = params.get("questionIds").toString().split(",");
        boolean delResult =false;
        try {
            int userId=(int)params.get("userId");
            delResult = questionDao.delete(questionIdArray,userId);
            if(delResult){
                //删除试题与用户的关系
                questionDao.delUser2Question(questionIdArray,userId);
            }
        } catch (Exception e) {
            logger.error("删除试题异常，试题ids="+params.get("questionIds"),e);
        }
        return ConvertResult.getSuccessResult(delResult);
    }

    @Override
    public boolean addQuestionAnswerAndAnalyze(List<QuestionEntity> questionEntityList) {
        if(questionEntityList==null || questionEntityList.isEmpty()) {
            return false;
        }else{
            try {
                for (QuestionEntity questionEntity : questionEntityList) {
                    questionDao.addQuestionAnswerAndAnalyze(questionEntity);
                }
                return true;
            }catch (Exception e){
                logger.error("拉取学科网答案与解析异常",e);
                return false;
            }
        }
    }

    @Override
    public List<QuestionEntity> searchQuestionListBySubId(int subjectId,int page,int pageSize) {
        if(subjectId==0){
            return null;
        }
        int firstIndex=(page-1)*pageSize;
        return questionDao.searchQuestionListBySubId(subjectId,firstIndex,pageSize);
    }

    /**
     * 根据试题ID获取试题答案和解析
     *
     * @param questionId 试题ID
     * @return Result
     * @author JayChen
     */
    @Override
    public Map<String, Object> getAnswerAndAnalysis(String questionId){
        return questionDao.getAnswerAndAnalysis(questionId);
    }

    @Override
    public Result collectQuestions(Map<String, Object> params) {
        if(params==null || params.isEmpty() || !params.containsKey("userId") || !params.containsKey("questionId")){
            return ConvertResult.getParamErrorResult("用户id或试题id不能为空");
        }
        params.put("type",0);
        if (questionDao.exitsCollectQuestions(params)>0) {
            Result errorResult = new Result(Result.Code.UnKnowError.value(), null, "已收藏该试题");
            return errorResult;
        }
        List<Map<String, Object>> questionList=new ArrayList<>();
        questionList.add(params);
        return ConvertResult.getSuccessResult(questionDao.collectQuestions(questionList));
    }

    @Override
    public Result collectQuestionsBasket(List<Map<String, Object>> params) {
        if(params==null || params.isEmpty()){
            return ConvertResult.getParamErrorResult("用户id或试题id不能为空");
        }
        List<Map<String,Object>> resultList=new ArrayList<>();
        for(Map<String,Object> question:params){
            if(questionDao.exitsCollectQuestions(question)==0){
                resultList.add(question);
            }
        }
        return ConvertResult.getSuccessResult(questionDao.collectQuestions(resultList));
    }

    @Override
    public Result searchQuestionsByUId(int userId,int type) {
        if(userId==0){
            return ConvertResult.getParamErrorResult("用户id不能为空");
        }
        Map<String,Object> result=getQuestionDetails(userId,type);
        return ConvertResult.getSuccessResult(result);
    }

    @Override
    public Result delCollectQuestions(Map<String,Object> params) {
        if(params==null || params.isEmpty()){
            return ConvertResult.getParamErrorResult("用户id不能为空");
        }
        int userId=(int)params.get("userId");
        int questionId=(int)params.get("questionId");
        int type=(int)params.get("type");
        return ConvertResult.getSuccessResult(questionDao.delCollectQuestions(userId,questionId,type));
    }

    @Override
    public Result searchQuestionsBasket(int userId, int type) {
        if(userId==0){
            return ConvertResult.getParamErrorResult("用户id不能为空");
        }
        List<Map<String,Object>> questionList=questionDao.searchQuestionsBasket(userId,type);
        Map<String,Object> result=new HashMap<>();
        int totalCount=0;
        if(questionList!=null && !questionList.isEmpty()){
            for(Map<String,Object> question:questionList){
                totalCount+=Integer.parseInt(String.valueOf(question.get("count")));
            }
        }
        result.put("question",questionList);
        result.put("totalCount",totalCount);
        return ConvertResult.getSuccessResult(result);
    }

    @Override
    public Result delQuestionsBasketByTypeId(Map<String, Object> params) {
        if(params==null || params.isEmpty() || !params.containsKey("userId") || !params.containsKey("typeId")){
            return ConvertResult.getParamErrorResult("用户id或题型id不能为空");
        }
        return ConvertResult.getSuccessResult(questionDao.delQuestionsBasketByTypeId(params));
    }

    @Override
    public Result searchQuestionBasketDetails(int userId, int type) {
        if(userId==0){
            return ConvertResult.getParamErrorResult("用户id不能为空");
        }
        Map<String,Object> result=getQuestionDetails(userId,type);
        return ConvertResult.getSuccessResult(result);
    }

    @Override
    public Result changeQuestionPublic(Map<String, Object> params) {
        if(params==null || params.isEmpty() || !params.containsKey("userId") || !params.containsKey("questionId") ||
                !params.containsKey("isPublic")){
            return ConvertResult.getParamErrorResult("参数为空或参数错误");
        }
        return ConvertResult.getSuccessResult(questionDao.changeQuestionPublic(params));
    }

    @Override
    public Result getQuestionByUIdAndPId(int userId, String isPublic, int pointId, int pointId2,
                                         int pointId3,int page,int pageSize) {
        if(userId==0 ){
            return ConvertResult.getParamErrorResult("用户id不能为空");
        }
        if(pointId==0){
            return ConvertResult.getParamErrorResult("知识点id不能为空");
        }
        Map<String,Object> params=new HashMap<>();
        params.put("userId",userId);
        if(!StringUtil.isEmpty(isPublic)){
            params.put("isPublic",isPublic);
        }
        params.put("questionPointId",pointId);
        if(pointId2!=0){
            params.put("questionPointId2",pointId2);
        }
        if(pointId3!=0){
            params.put("questionPointId3",pointId3);
        }
        int firstIndex=(page-1)*pageSize;
        params.put("firstIndex",firstIndex);
        params.put("pageSize",pageSize);
        List<QuestionEntity> questionList=questionDao.searchQuestionsByUIdAndPId(params);
        int count=questionDao.searchUploadQuestionsCount(params);
        Page pageResult=new Page(questionList,page,pageSize,count);
        return ConvertResult.getSuccessResult(pageResult);
    }

    /**
     * 返回试题列表
     * @param userId
     * @param type
     * @return
     */
   private Map<String,Object> getQuestionDetails(int userId,int type){
       List<QuestionEntity> list=questionDao.searchQuestionsByUId(userId,type);
       Map<String,Object> result=new HashMap<>();
       if(list!=null && !list.isEmpty()) {
           result.put("question", list);
           result.put("totalCount", list.size());
       }else{
           result.put("question", "");
           result.put("totalCount", 0);
       }
       return result;
   }

    @Override
    public Result searchPublicQuestions(int pointId, int pointId2,int pointId3,int page,int pageSize) {
        if(pointId==0){
            return ConvertResult.getParamErrorResult("知识点id不能为空");
        }
        Map<String,Object> params=new HashMap<>();
        params.put("questionPointId",pointId);
        if(pointId2!=0){
            params.put("questionPointId2",pointId2);
        }
        if(pointId3!=0){
            params.put("questionPointId3",pointId3);
        }
        int firstIndex=(page-1)*pageSize;
        params.put("firstIndex",firstIndex);
        params.put("pageSize",pageSize);
        List<QuestionEntity> questionList=questionDao.searchPublicQuestions(params);
        int count=questionDao.searchPublicQuestionsCount(params);
        Page pageResult=new Page(questionList,page,pageSize,count);
        return ConvertResult.getSuccessResult(pageResult);
    }
}
