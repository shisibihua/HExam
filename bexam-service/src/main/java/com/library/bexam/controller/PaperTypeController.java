package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.PaperTypeEntity;
import com.library.bexam.entity.QuestionTypeEntity;
import com.library.bexam.service.PaperTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.alicp.jetcache.Cache.logger;

/**
 * 试卷类型控制器
 *
 * @author JayChen
 */
@RestController
@CrossOrigin
@RequestMapping("/paperType")
public class PaperTypeController {

    @Autowired
    private PaperTypeService paperTypeService;

    /**
     * 获取试卷类型列表
     *
     * @return
     */
    @GetMapping("/list")
    public Result list() {
        Result result = new Result(Result.Code.UnKnowError.value(), "获取试卷类型列表失败");

        try {
            List list = paperTypeService.list();
            result.setResult(list);
            result.setCode(Result.Code.Success.value());
            result.setMsg("获取试卷类型列表成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 添加试卷类型
     *
     * @param paperTypeEntity
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody PaperTypeEntity paperTypeEntity) {
        Result result = new Result(Result.Code.UnKnowError.value(), "添加试卷类型失败");

        try {
            boolean addResult = paperTypeService.add(paperTypeEntity);
            result.setCode(Result.Code.Success.value());
            result.setMsg("添加试卷类型成功");
            result.setResult(addResult);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 删除试卷类型
     *
     * @param params
     * @return
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String, String> params) {
        Result result = new Result(Result.Code.UnKnowError.value(), "删除试卷类型失败");
        if (params.isEmpty() || !params.containsKey("typeIds")) {
            return result;
        }
        String[] typeIdArray = params.get("typeIds").split(",");

        try {
            boolean addResult = paperTypeService.delete(typeIdArray);
            result.setResult(addResult);
            if (addResult) {
                result.setCode(Result.Code.Success.value());
                result.setMsg("删除试卷类型成功");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;

    }

    /**
     * 更新试卷类型信息
     *
     * @param paperTypeEntity
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody PaperTypeEntity paperTypeEntity) {
        Result result = new Result(Result.Code.UnKnowError.value(), "修改试卷类型失败");

        try {
            boolean updateResult = paperTypeService.update(paperTypeEntity);
            result.setResult(updateResult);
            if (updateResult) {
                result.setCode(Result.Code.Success.value());
                result.setMsg("修改试卷类型成功");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 根据ID获取试卷类型信息
     *
     * @return Result
     * @author JayChen
     */
    @GetMapping("/{typeId}")
    public Result get(@PathVariable("typeId") int typeId) {
        Result result = new Result(Result.Code.UnKnowError.value(), "获取试卷类型失败");

        try {
            PaperTypeEntity paperTypeEntity = paperTypeService.get(typeId);
            result.setResult(paperTypeEntity);
            if (paperTypeEntity != null) {
                result.setCode(Result.Code.Success.value());
                result.setMsg("获取试卷类型成功");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }
}
