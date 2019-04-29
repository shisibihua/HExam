package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.service.ScoreClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Map;

import static com.alicp.jetcache.Cache.logger;

/**
 * @author JayChen
 */
@RestController
@CrossOrigin
@RequestMapping("/scoreClass")
public class ScoreClassController {

    @Autowired
    private ScoreClassService scoreClassService;

    /**
     * 获取参与考试的班级
     * 
     * @author JayChen
     * @return Result
     */
    @GetMapping("/getClassByPaperId")
    public Result getClassByPaperId(@RequestParam("paperId") String paperId) {
        Result result = new Result(Result.Code.UnKnowError.value(), "获取班级列表失败");

        List list;

        try {
            list = scoreClassService.getClassByPaperId(paperId);
            if (list != null) {
                result.setResult(list);
                result.setMsg("获取班级列表成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 获取试卷考试班级的统计信息
     *
     * @return
     * @author JayChen
     */
    @GetMapping("/list")
    public Result list(@RequestParam("paperId") String paperId) {
        Result result = new Result(Result.Code.Success.value());
        List list;
        try {
            list = scoreClassService.list(paperId);
            result.setResult(list);
        } catch (Exception e) {
            logger.error("获取班级得分异常",e);
            result.setCode(Result.Code.UnKnowError.value());
            result.setMsg("获取班级得分异常");
        }

        return result;
    }

    /**
     * 获取试卷得分信息
     *
     * @return Result
     * @author JayChen
     */
    @GetMapping("/getPaperScoreInfo")
    public Result getPaperScoreInfo(@RequestParam("paperId") String paperId) {
        Result result = new Result(Result.Code.UnKnowError.value(), "失败");

        Map list;

        try {
            list = scoreClassService.getPaperScoreInfo(paperId);
            if (list != null) {
                result.setResult(list);
                result.setMsg("成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

}
