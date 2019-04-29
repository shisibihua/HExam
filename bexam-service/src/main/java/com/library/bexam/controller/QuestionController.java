package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Page;
import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.common.util.TipsMessage;
import com.library.bexam.entity.QuestionEntity;
import com.library.bexam.service.QuestionService;
import com.library.bexam.util.ConvertResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.alicp.jetcache.Cache.logger;

/**
 * 试题控制器
 *
 * @author JayChen
 */
@RestController
@CrossOrigin
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 添加试题
     *
     * @param questionEntityList JsonArray
     * @return Result
     * @author JayChen
     */
    @PostMapping("/add")
    public Result add(@RequestBody List<QuestionEntity> questionEntityList) {
        return questionService.batchAdd(questionEntityList);
    }

    /**
     * 获取试题列表
     *
     * @param params Map<questionPointId, questionNodeId, keyword, page, pageSize>
     * @return List
     * @author JayChen
     */
    @PostMapping("/list")
    public Result list(@RequestBody Map<String, Object> params) {
        Result result = new Result();

        // 题型，难易度，年级
        if (params.size() == 0 || !params.containsKey("page") || !params.containsKey("pageSize") || !params.containsKey("subjectId")) {
            result.setCode(Result.Code.ParamError.value());
            result.setMsg("获取试题列表失败,参数错误");
            result.setResult(false);
            return result;
        }

        int page = (int) params.get("page") <= 0 ? 1 : (int) params.get("page");
        int pageSize = (int) params.get("pageSize") <= 0 ? 5 : (int) params.get("pageSize");
        params.put("currentIndex", (page - 1) * pageSize);
        try {
            if(!params.containsKey("sortType")){
                params.put("sortType",0);
            }
            int totalCount = questionService.getTotalCount(params);
            List list = questionService.list(params);
            Page pageResult = new Page(list, page, pageSize, totalCount);
            result.setCode(Result.Code.Success.value());
            result.setResult(pageResult);
        }catch (Exception e){
            logger.error("分页查询试题列表异常",e);
            result.setCode(Result.Code.NoSuchMethod.value());
            result.setMsg("分页查询试题列表异常");
        }
        return result;
    }

    /**
     * 根据ID获取试题信息
     *
     * @param questionId 试题ID
     * @return Result
     * @author JayChen
     */
    @GetMapping("{questionId}")
    public Result get(@PathVariable("questionId") String questionId) {
        Result result = new Result(Result.Code.UnKnowError.value(), false, "获取试题信息失败");

        try {
            QuestionEntity questionEntity = questionService.getQuestionById(questionId);
            if (questionEntity != null) {
                result.setMsg("获取试题信息成功");
                result.setCode(Result.Code.Success.value());
                result.setResult(questionEntity);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 更新试题信息
     *
     * @param questionEntity 试题实体
     * @return Result
     * @author JayChen
     */
    @PostMapping("/update")
    public Result update(@RequestBody QuestionEntity questionEntity) {
        Result result = new Result(Result.Code.UnKnowError.value(), false, "修改试题信息失败");

        try {
            boolean addResult = questionService.update(questionEntity);
            result.setResult(addResult);
            if (addResult) {
                result.setMsg("添加试题成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 删除试题
     *
     * @param params Map<"questionIds", "1,32,34,66,99">
     * @return Result
     * @author JayChen
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String, Object> params) {
        return questionService.delete(params);
    }

    /**
     * 根据试题ID获取试题答案和解析
     *
     * @param questionId 试题ID
     * @return Result
     * @author JayChen
     */
    @GetMapping("/answer/{questionId}")
    public Result getAnswerAndAnalysis(@PathVariable("questionId") String questionId) {
        Result result = new Result(Result.Code.UnKnowError.value(), false, "获取试题答案解析失败");

        try {
            Map<String, Object> getResult = questionService.getAnswerAndAnalysis(questionId);
            result.setResult(getResult);
            if (getResult != null && !getResult.isEmpty()) {
                result.setMsg("获取试题答案解析成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 收藏试题
     * @author caoqian
     * @date 20181230
     * @return
     */
    @RequestMapping(value = "collectQuestions",method = RequestMethod.POST)
    public Result collectQuestions(@RequestBody Map<String,Object> params){
        return questionService.collectQuestions(params);
    }

    /**
     * 加入试题篮
     * @author caoqian
     * @date 20181230
     * @return
     */
    @RequestMapping(value = "collectQuestionsBasket",method = RequestMethod.POST)
    public Result collectQuestionsBasket(@RequestBody List<Map<String,Object>> params){
        return questionService.collectQuestionsBasket(params);
    }

    /**
     * 查询老师收藏的试题
     * @param userId 用户id
     * @author caoqian
     * @date 20181230
     * @return
     */
    @RequestMapping(value = "searchCollectQuestions",method = RequestMethod.GET)
    public Result searchCollectQuestions(int userId){
        return questionService.searchQuestionsByUId(userId,TipsMessage.QUES_COLLECTION);
    }

    /**
     * 查询试题篮试题详情
     * @param userId 用户id
     * @author caoqian
     * @date 20181230
     * @return
     */
   @RequestMapping(value = "searchQuestionBasketDetails",method = RequestMethod.GET)
    public Result searchQuestionBasketDetails(int userId){
        return questionService.searchQuestionBasketDetails(userId,TipsMessage.QUES_BASKET);
    }

    /**
     * 查询试题篮信息
     * @param userId  用户id
     * @return
     */
    @RequestMapping(value = "searchQuestionsBasket",method = RequestMethod.GET)
    public Result searchQuestionsBasket(int userId){
        return questionService.searchQuestionsBasket(userId, TipsMessage.QUES_BASKET);
    }

    /**
     * 删除老师收藏的试题/试题篮
     * @author caoqian
     * @return
     */
    @RequestMapping(value = "delCollectQuestions",method = RequestMethod.POST)
    public Result delCollectQuestions(@RequestBody Map<String,Object> params){
        return questionService.delCollectQuestions(params);
    }

    /**
     * 按题型删除试题篮
     * @author caoqian
     * @return
     */
    @RequestMapping(value = "delQuestionsBasket",method = RequestMethod.POST)
    public Result delQuestionsBasket(@RequestBody Map<String,Object> params){
        return questionService.delQuestionsBasketByTypeId(params);
    }

    /**
     * 修改试题是否公开的状态
     * @param {"userId":1,"questionId":1,"isPublic"：0}
     * @author caoqian
     * @return
     */
    @RequestMapping(value = "changeQuestionPublic",method = RequestMethod.POST)
    public Result changeQuestionPublic(@RequestBody Map<String,Object> params){
        return questionService.changeQuestionPublic(params);
    }

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
     * @author caoqian
     * @date 20190111
     */
    @RequestMapping(value = "searchQuestionByUIdAndPId",method = RequestMethod.GET)
    public Result searchQuestionByUIdAndPId(int userId,String isPublic,int pointId,int pointId2,int pointId3,
                                            int page,int pageSize){
        return questionService.getQuestionByUIdAndPId(userId,isPublic,pointId,pointId2,pointId3,page,pageSize);
    }

    /**
     * 根据知识点获取学科网试题及所有用户上传的试题
     * @param pointId      一级知识点
     * @param pointId2     二级知识点
     * @param pointId3     三级知识点
     * @param page         当前页
     * @param pageSize     每页条数
     * @return
     */
    @RequestMapping(value = "searchPublicQuestions",method = RequestMethod.GET)
    public Result searchPublicQuestions(int pointId,int pointId2,int pointId3,int page,int pageSize){
        return questionService.searchPublicQuestions(pointId,pointId2,pointId3,page,pageSize);
    }
}