package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.QuestionTypeEntity;
import com.library.bexam.service.QuestionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.alicp.jetcache.Cache.logger;

/**
 * 题型控制器
 *
 * @author JayChen
 */
@RestController
@CrossOrigin
@RequestMapping("/questionType")
public class QuestionTypeController {

    @Autowired
    private QuestionTypeService questionTypeService;

    /**
     * 获取题型列表
     *
     * @param subjectId 科目id
     * @return
     */
    @GetMapping("/list")
    public Result list(int subjectId) {
        Result result = new Result(Result.Code.UnKnowError.value(), "获取题型列表失败");

        try {
            List list = questionTypeService.list(subjectId);
            result.setCode(Result.Code.Success.value());
            result.setMsg("获取题型列表成功");
            result.setResult(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }


        return result;
    }

    /**
     * 根据ID获取题型信息
     *
     * @return Result
     * @author JayChen
     */
    @GetMapping("{typeId}")
    public Result get(@PathVariable("typeId") String typeId) {
        Result result = new Result(Result.Code.Success.value(), false, "获取试题类型信息失败");

        try {
            QuestionTypeEntity questionTypeEntity = questionTypeService.getQuestionTypeById(typeId);
            if (questionTypeEntity != null) {
                result.setMsg("获取试题类型信息成功");
                result.setCode(Result.Code.Success.value());
                result.setResult(questionTypeEntity);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 添加题型
     *
     * @param questionTypeEntity
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody QuestionTypeEntity questionTypeEntity) {
        Result result = new Result(Result.Code.UnKnowError.value(), "添加题型失败");
        try {
            boolean addResult = questionTypeService.add(questionTypeEntity);
            result.setCode(Result.Code.Success.value());
            result.setMsg("添加题型成功");
            result.setResult(addResult);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 批量添加题型
     *
     * @param questionTypeEntities List
     * @return Result
     * @author JayChen
     */
    @PostMapping("/batchAdd")
    public Result batchAdd(@RequestBody List<QuestionTypeEntity> questionTypeEntities) {
        Result result = new Result(Result.Code.Success.value(), false, "添加试题类型失败");

        try {
            boolean addResult = questionTypeService.batchAdd(questionTypeEntities,false);
            result.setResult(addResult);
            if (addResult) {
                result.setMsg("添加试题类型成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 删除题型
     *
     * @param params
     * @return
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String, String> params) {
        Result result = new Result(Result.Code.UnKnowError.value(), "删除题型失败");
        if (params.isEmpty() || !params.containsKey("typeIds")) {
            return result;
        }

        String[] typeIdArray = params.get("typeIds").split(",");

        try {
            boolean addResult = questionTypeService.delete(typeIdArray);
            result.setResult(addResult);
            if (addResult) {
                result.setCode(Result.Code.Success.value());
                result.setMsg("删除题型成功");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;

    }

    /**
     * 更新题型
     *
     * @param questionTypeEntity
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody QuestionTypeEntity questionTypeEntity) {
        return questionTypeService.update(questionTypeEntity);
    }
}
