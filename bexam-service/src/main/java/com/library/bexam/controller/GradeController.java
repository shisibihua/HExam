package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.GradeEntity;
import com.library.bexam.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 年级处理表现层
 * @author caoqina
 * @date 20181216
 */
@CrossOrigin
@RestController
@RequestMapping("grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * 添加年级
     * @param gradeEntity
     * @return
     */
    @RequestMapping(value="add",method = RequestMethod.POST)
    public Result addGrade(@RequestBody GradeEntity gradeEntity){
        return gradeService.addGrade(gradeEntity);
    }

    /**
     * 根据年级ids删除年级，支持批量删除
     * @param gradeEntity
     * @return
     */
    @RequestMapping(value="delete",method = RequestMethod.POST)
    public Result deleteGrade(@RequestBody Map<String,String> gradeEntity){
        return gradeService.deleteGrade(gradeEntity);
    }

    /**
     * 修改年级
     * @param gradeEntity
     * @return
     */
    @RequestMapping(value="update",method = RequestMethod.POST)
    public Result updateGrade(@RequestBody GradeEntity gradeEntity){
        return gradeService.updateGrade(gradeEntity);
    }

    /**
     * 根据学段id查询年级信息，年级id为空时查询所有年级列表
     * @param periodId  学段id
     * @return
     */
    @RequestMapping(value="list",method = RequestMethod.GET)
    public Result searchGradeList(int periodId){
        return gradeService.searchGradeListByPeriodId(periodId);
    }

    /**
     * 根据年级id查询年级信息
     * @param gradeId  年级id
     * @return
     */
    @RequestMapping(value="getGradeById",method = RequestMethod.GET)
    public Result getGradeById(int gradeId){
        return gradeService.searchGradeById(gradeId);
    }
}
