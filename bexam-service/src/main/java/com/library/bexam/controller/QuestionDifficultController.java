package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.QuestionDifficultEntity;
import com.library.bexam.entity.QuestionEntity;
import com.library.bexam.service.QuestionDifficultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.alicp.jetcache.Cache.logger;

/**
 * 试题难易度控制器
 *
 * @author JayChen
 */
@RestController
@CrossOrigin
@RequestMapping("questionDifficult")
public class QuestionDifficultController {

    @Autowired
    private QuestionDifficultService questionDifficultService;

    /**
     * 获取难易度列表
     *
     * @return
     */
    @GetMapping("/list")
    public Result list() {
        Result result = new Result(Result.Code.UnKnowError.value(), false, "获取难度列表失败");

        try {
            List list = questionDifficultService.list();
            result.setResult(list);
            result.setCode(Result.Code.Success.value());
            result.setMsg("获取难度列表成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 根据ID获取难易度信息
     *
     * @return Result
     * @author JayChen
     */
    @GetMapping("{difficultId}")
    public Result get(@PathVariable("difficultId") String questionDifficultId) {
        Result result = new Result(Result.Code.Success.value(), false, "获取试题难易度信息失败");

        try {
            QuestionDifficultEntity questionDifficultEntity = questionDifficultService.getQuestionDifficultById(questionDifficultId);
            if (questionDifficultEntity != null) {
                result.setMsg("获取试题难易度信息成功");
                result.setCode(Result.Code.Success.value());
                result.setResult(questionDifficultEntity);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 添加难易度
     *
     * @param questionDifficultEntity
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody QuestionDifficultEntity questionDifficultEntity) {
        Result result = new Result(Result.Code.UnKnowError.value(), "添加难度失败");

        try {
            boolean addResult = questionDifficultService.add(questionDifficultEntity);
            result.setResult(addResult);
            result.setCode(Result.Code.Success.value());
            result.setMsg("添加难度成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;

    }

    /**
     * 删除难易度
     *
     * @param params
     * @return
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String, String> params) {
        Result result = new Result(Result.Code.UnKnowError.value(), "删除试题难易度失败");

        if (params.isEmpty() || !params.containsKey("difficultIds")) {
            return result;
        }
        String[] difficultIdArray = params.get("difficultIds").split(",");

        try {
            boolean addResult = questionDifficultService.delete(difficultIdArray);
            result.setResult(addResult);
            if (addResult) {
                result.setCode(Result.Code.Success.value());
                result.setMsg("删除试题难易度成功");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 更新难易度信息
     *
     * @param questionDifficultEntity
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody QuestionDifficultEntity questionDifficultEntity) {
        Result result = new Result(Result.Code.UnKnowError.value(), "修改难易度失败");

        try {
            boolean addResult = questionDifficultService.update(questionDifficultEntity);
            result.setCode(Result.Code.Success.value());
            result.setMsg("修改难易度成功");
            result.setResult(addResult);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }
}
