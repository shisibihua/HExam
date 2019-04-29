package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.dataacquisition.ExamDataAcquistion;
import com.library.bexam.entity.QuestionEntity;
import com.library.bexam.util.ConvertResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 学科网拉取数据处理表现层
 * @author caoqian
 * @date 20181216
 */
@CrossOrigin
@RestController
@RequestMapping("data")
public class ExamDataController {
    @Autowired
    private ExamDataAcquistion examDataAcquistion;

    /**
     * 拉取科目
     * @return
     */
    @RequestMapping(value = "getSubjects",method = RequestMethod.GET)
    public Result getSubjects(){
        return ConvertResult.getSuccessResult(examDataAcquistion.getSubjects());
    }

    /**
     * 拉取教材版本
     * @return
     */
    @RequestMapping(value = "getVersions",method = RequestMethod.GET)
    public Result getVersions(){
        return ConvertResult.getSuccessResult(examDataAcquistion.getVersions());
    }

    /**
     * 拉取教材册别
     * @return
     */
    @RequestMapping(value = "getTextBooks",method = RequestMethod.GET)
    public Result getTextBooks(){
        return ConvertResult.getSuccessResult(examDataAcquistion.getTextBooks());
    }

    /**
     * 根据学段拉取教材章节
     * @return
     */
    @RequestMapping(value = "getNodeBooks",method = RequestMethod.POST)
    public Result getNodeBooksByPeriodIds(@RequestBody Map<String,String> periodIds){
        if(periodIds==null || periodIds.isEmpty()){
            return ConvertResult.getParamErrorResult("学段ids不能为空");
        }
        return ConvertResult.getSuccessResult(examDataAcquistion.getNodeBooks(periodIds));
    }
    /**
     * 根据学科id拉取知识点
     * @return
     */
    @RequestMapping(value = "getPoints",method = RequestMethod.GET)
    public Result getPointsByPeriodIds(int subjectId){
        return ConvertResult.getSuccessResult(examDataAcquistion.getPoints(subjectId));
    }

    /**
     * 根据教材章节获取试题列表(更新版本、册别、章节id)
     * @param params
     * @return
     */
    @RequestMapping(value = "getChapterQuestionsList",method = RequestMethod.POST)
    public Result getChapterQuestionsList(@RequestBody Map<String,Object> params){
        if(params==null || params.isEmpty() || !params.containsKey("subjectId") || !params.containsKey("startPage") || !params.containsKey("countPerPage")){
            return ConvertResult.getParamErrorResult("科目id或分页参数不能为空");
        }
        return ConvertResult.getSuccessResult(examDataAcquistion.getChaptersList(params));
    }

    /**
     * 根据知识点获取试题列表
     * @param params
     * @return
     */
    @RequestMapping(value = "addKnowledgePointsList",method = RequestMethod.POST)
    public Result addKnowledgePointsList(@RequestBody Map<String,Object> params){
        if(params==null || params.isEmpty() || !params.containsKey("startPage") || !params.containsKey("countPerPage")){
            return ConvertResult.getParamErrorResult("分页参数不能为空");
        }
        return ConvertResult.getSuccessResult(examDataAcquistion.saveKnowledgePointQuestionsList(params));
    }

    @RequestMapping(value = "updateKnowledgePointsList",method = RequestMethod.POST)
    public Result updateKnowledgePointsList(@RequestBody Map<String,Object> params){
        if(params==null || params.isEmpty() || !params.containsKey("startPage") || !params.containsKey("countPerPage")){
            return ConvertResult.getParamErrorResult("分页参数不能为空");
        }
        return ConvertResult.getSuccessResult(examDataAcquistion.updateKnowledgePointsList(params));
    }

    /**
     * 获取单个试题的答案和解析
     * @param subjectId 科目id
     * @return
     */
    @RequestMapping(value="addQuestionAnswerAndAnalyze",method = RequestMethod.GET)
    public Result addQuestionAnswerAndAnalyze(int subjectId,int page,int pageSize){
        if(subjectId==0){
            return ConvertResult.getParamErrorResult("科目id不能为空");
        }
        return ConvertResult.getSuccessResult(examDataAcquistion.addQuestionAnswerAndAnalyze(subjectId,page,pageSize));
    }

    /**
     * 根据学段id拉取试题类型
     * @return
     */
    @RequestMapping(value ="addQuestionType",method = RequestMethod.GET)
    public Result addQuestionType(int periodId){
        return ConvertResult.getSuccessResult(examDataAcquistion.addQuestionType(periodId));
    }
}
