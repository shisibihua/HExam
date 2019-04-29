package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.service.ScoreStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.alicp.jetcache.Cache.logger;

/**
 * @author JayChen
 */
@RestController
@CrossOrigin
@RequestMapping("/scoreStudent")
public class ScoreStudentController {

    @Autowired
    private ScoreStudentService scoreStudentService;

    /**
     * 条件检索学生得分列表
     *
     * @param params Map<page, pageSize>
     * @return List
     * @author JayChen
     */
    @PostMapping("/list")
    public Result list(@RequestBody Map<String, Object> params) {
        Result result = new Result(Result.Code.UnKnowError.value(), false, "获取列表失败");

        int page = (int) params.get("page") <= 0 ? 1 : (int) params.get("page");
        int pageSize = (int) params.get("pageSize") <= 0 ? 5 : (int) params.get("pageSize");

        params.put("currentIndex", (page - 1) * pageSize);

        try {
            List list = scoreStudentService.list(params);
            result.setResult(list);
            if (list != null) {
                result.setMsg("获取列表成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 获取试卷考试班级的排名
     *
     * @return
     * @author JayChen
     */
    @GetMapping("/getRankingList")
    public Result getRankingList(@RequestParam("paperId") String paperId, @RequestParam("classId") int classId) {
        Result result = new Result(Result.Code.UnKnowError.value(), "获取排名列表失败");

        Map list;

        try {
            list = scoreStudentService.getRankingListByClassId(paperId, classId);
            if (list != null) {
                result.setResult(list);
                result.setMsg("获取排名列表成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 根据试卷ID获取各班级各个成绩段学生列表
     * 
     * @author JayChen
     * @return Result
     */
    @GetMapping("/getClassLevelInfoByPaperId")
    public Result getClassLevelInfoByPaperId(@RequestParam("paperId") String paperId) {
        Result result = new Result(Result.Code.UnKnowError.value(), "失败");

        List list;

        try {
            list = scoreStudentService.getClassLevelInfoByPaperId(paperId);
            if (list != null) {
                result.setResult(list);
                result.setMsg("成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 获取需要关注的学生名单
     *
     * @return Result
     * @author JayChen
     */
    @GetMapping("/getAttentionStudent")
    public Result getAttentionStudent(@RequestParam("paperId") String paperId, @RequestParam("classId") int classId) {
        Result result = new Result(Result.Code.UnKnowError.value(), "获取失败");

        List list;

        try {
            list = scoreStudentService.getAttentionStudent(paperId, classId);
            if (list != null) {
                result.setResult(list);
                result.setMsg("获取成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }
}
