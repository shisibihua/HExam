package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.PaperScoreEntity;
import com.library.bexam.service.PaperScoreService;
import com.sun.xml.internal.xsom.impl.ListSimpleTypeImpl;
import io.netty.handler.ssl.ReferenceCountedOpenSslClientContext;
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
@RequestMapping("paperScore")
public class PaperScoreController {

    @Autowired
    private PaperScoreService paperScoreService;

    /**
     * 添加试卷得分情况
     *
     * @return Result
     * @author JayChen
     */
    @PostMapping("/add")
    public Result add(@RequestBody List<PaperScoreEntity> paperScoreEntityList) {
        Result result = new Result(Result.Code.UnKnowError.value(), false, "添加试卷分数失败");

        try {
            boolean addResult = paperScoreService.add(paperScoreEntityList);
            result.setResult(addResult);
            if (addResult) {
                result.setMsg("添加试卷分数成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 获取试卷得分列表
     *
     * @return Result
     * @author JayChen
     */
    @PostMapping("/list")
    public Result list(@RequestBody Map<String, Object> params) {
        Result result = new Result(Result.Code.UnKnowError.value(), false, "获取试卷得分失败");

        try {
            List addResult = paperScoreService.list(params);
            result.setResult(addResult);
            if (addResult != null) {
                result.setMsg("获取试卷得分成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 删除试卷得分
     *
     * @return Result
     * @author JayChen
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String, Object> params) {
        Result result = new Result(Result.Code.UnKnowError.value(), false, "删除试卷得分失败");

        if (!params.containsKey("studentIds") || !params.containsKey("paperIds")) {
            result.setMsg("缺少参数");
            return result;
        }

        String[] paperIds = params.get("paperIds").toString().split(",");
        String[] studentIds = params.get("studentIds").toString().split(",");

        boolean addResult = false;
        try {

            addResult = paperScoreService.delete(paperIds, studentIds);

            result.setResult(addResult);
            if (addResult) {
                result.setMsg("删除试卷得分成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }


    /**
     * 根据试卷ID获取某个班级试卷所有试题得分率信息
     *
     * @param params Map<page, pageSize>
     * @return List
     * @author JayChen
     */
    @PostMapping("/getScoringRateByClassId")
    public Result getScoringRateByClassId(@RequestBody Map<String, Object> params) {
        Result result = new Result(Result.Code.UnKnowError.value(), false, "获取列表失败");

        if (params.size() == 0 || !params.containsKey("classId") || !params.containsKey("paperId")) {
            result.setCode(Result.Code.ParamError.value());
            result.setMsg("获取失败，参数错误");
            result.setResult(false);
            return result;
        }

        try {
            List list = paperScoreService.getScoringRateByClassId(params.get("paperId").toString(), params.get("classId").toString());
            result.setResult(list);
            if (list != null) {
                result.setMsg("获取成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 获取试题错误名单
     *
     * @return Result
     * @author JayChen
     */
    @GetMapping("/getWrongStudent")
    public Result getWrongStudent(@RequestParam("paperContentId") String paperContentId) {
        Result result = new Result(Result.Code.UnKnowError.value(), "失败");

        List list;

        try {
            list = paperScoreService.getWrongStudent(paperContentId);
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
     * 获取试题错误率信息
     *
     * @return Result
     * @author JayChen
     */
    @GetMapping("/getWrongRate")
    public Result getWrongRate(@RequestParam("paperContentId") String paperContentId) {
        Result result = new Result(Result.Code.UnKnowError.value(), "失败");

        List list;

        try {
            list = paperScoreService.getWrongRate(paperContentId);
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
     * 获取各个班级各个题的得分率
     *
     * @author JayChen
     * @return Result
     */
    @GetMapping("/getScoringRate")
    public Result getScoringRate(@RequestParam("paperId") String paperId) {
        Result result = new Result(Result.Code.UnKnowError.value(), "失败");

        List list;

        try {
            list = paperScoreService.getScoringRate(paperId);
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
}
