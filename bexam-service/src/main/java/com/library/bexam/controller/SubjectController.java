package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.SubjectEntity;
import com.library.bexam.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;
    /**
     * 添加科目
     * @param subjectEntity
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Result addSubject(@RequestBody SubjectEntity subjectEntity){
        return subjectService.addSubject(subjectEntity);
    }

    /**
     * 根据科目id查询科目信息
     * @param subjectId  科目id
     * @return
     */
    @RequestMapping(value = "getSubjectById",method = RequestMethod.GET)
    public Result getSubjectById(int subjectId){
        return subjectService.searchSubjectById(subjectId);
    }

    /**
     * 根据科目ids删除科目信息，支持批量删除
     * @param params
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public Result deleteSujectByIds(@RequestBody Map<String,String> params){
        return subjectService.deleteSubjectByIds(params);
    }

    /**
     * 修改科目信息
     * @param subjectEntity
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Result updateSubject(@RequestBody SubjectEntity subjectEntity){
        return subjectService.updateSubjectInfo(subjectEntity);
    }

    /**
     * 获取科目列表
     * @param periodId 学段id,为0时，查询所有学段
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Result searchSubjectList(int periodId){
        return subjectService.searchSubjectList(periodId);
    }

    /**
     * 根据科目id查询教材版本、册别、章节信息
     * @param subjectId
     * @author caoqian
     * @return
     */
    @RequestMapping(value = "searchExamList",method = RequestMethod.GET)
    public Result searchExamList(int subjectId){
        return subjectService.searchExamList(subjectId);
    }
}
