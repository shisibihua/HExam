package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.PaperContentEntity;
import com.library.bexam.entity.PaperEntity;
import com.library.bexam.service.PaperService;
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
@RequestMapping("/paper")
public class PaperController {
    @Autowired
    private PaperService paperService;

    /**
     * 添加试卷
     *
     * @param paperEntityList JsonArray
     * @return Result
     * @author JayChen
     */
    @PostMapping("/add")
    public Result add(@RequestBody List<PaperEntity> paperEntityList) {
        return paperService.add(paperEntityList);
    }

    /**
     * 获取试卷列表
     *
     * @param params Map<paperPointId, paperNodeId, page, pageSize>
     * @return List
     * @author JayChen
     */
    @PostMapping("/list")
    public Result list(@RequestBody Map<String, Object> params) {
        Result result = new Result(Result.Code.UnKnowError.value(), false, "获取试卷列表失败");

//        if (params.size() == 0 || !params.containsKey("paperPointId") || !params.containsKey("paperNodeId") || !params.containsKey("page") || !params.containsKey("pageSize")) {
//            result.setCode(Result.Code.ParamError.value());
//            result.setMsg("获取试卷列表失败，参数错误");
//            result.setResult(false);
//            return result;
//        }
//
        int page = (int) params.get("page") <= 0 ? 1 : (int) params.get("page");
        int pageSize = (int) params.get("pageSize") <= 0 ? 5 : (int) params.get("pageSize");

        params.put("currentIndex", (page - 1) * pageSize);

        System.out.println(params);

        try {
            List list = paperService.list(params);
            result.setResult(list);
            if (list != null) {
                result.setMsg("获取试卷列表成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 根据ID获取试卷信息
     *
     * @param paperId 试卷ID
     * @return Result
     * @author JayChen
     */
    @GetMapping("{paperId}")
    public Result get(@PathVariable("paperId") String paperId) {
        Result result = new Result(Result.Code.UnKnowError.value(), false, "获取试卷信息失败");

        try {
            PaperEntity paperEntity = paperService.get(paperId);
            if (paperEntity != null) {
                result.setMsg("获取试卷信息成功");
                result.setCode(Result.Code.Success.value());
                result.setResult(paperEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 更新试卷信息
     *
     * @param paperEntity 试卷实体
     * @return Result
     * @author JayChen
     */
    @PostMapping("/update")
    public Result update(@RequestBody PaperEntity paperEntity) {
        Result result = new Result(Result.Code.UnKnowError.value(), false, "修改试卷信息失败");

        try {
            boolean addResult = paperService.update(paperEntity);
            result.setResult(addResult);
            if (addResult) {
                result.setMsg("修改试卷信息成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 更新试卷试题信息
     *
     * @return Result
     * @author JayChen
     */
    @PostMapping("/updateContent")
    public Result updateContent(@RequestBody PaperEntity paperEntity) {
        Result result = new Result(Result.Code.UnKnowError.value(), false, "修改试卷试题内容失败");

        try {
            boolean addResult = paperService.updateContent(paperEntity);
            result.setResult(addResult);
            if (addResult) {
                result.setMsg("修改试卷信息成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * 删除试卷
     *
     * @param params Map<"paperIds", "1,32,34,66,99">
     * @return Result
     * @author JayChen
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String, String> params) {
        Result result = new Result(Result.Code.UnKnowError.value(), false, "删除试卷失败");

        if (params.isEmpty() || !params.containsKey("paperIds")) {
            return result;
        }
        String[] paperIdArray = params.get("paperIds").split(",");

        try {
            boolean addResult = paperService.delete(paperIdArray);
            result.setResult(addResult);
            if (addResult) {
                result.setMsg("删除试卷成功");
                result.setCode(Result.Code.Success.value());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }
}
