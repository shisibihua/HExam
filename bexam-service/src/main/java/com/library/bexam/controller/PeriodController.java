package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.PeriodEntity;
import com.library.bexam.entity.SchoolEntity;
import com.library.bexam.service.PeriodService;
import com.library.bexam.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 学段处理表现层
 * @author caoqina
 * @date 20181215
 */
@CrossOrigin
@RestController
@RequestMapping("period")
public class PeriodController {
    @Autowired
    private PeriodService periodService;
    @Autowired
    private SchoolService schoolService;

    /**
     * 添加学段
     * @param periodEntity
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Result addPeriod(@RequestBody PeriodEntity periodEntity){
        return periodService.addPeriod(periodEntity);
    }

    /**
     * 修改学段
     * @param periodEntity
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Result updatePeriod(@RequestBody PeriodEntity periodEntity){
        return periodService.updatePeriod(periodEntity);
    }

    /**
     * 根据学段ids删除学段,支持批量删除
     * @param periodId 多个","分割
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public Result deletePeriod(@RequestBody Map<String,String> periodId){
        return periodService.deletePeriodById(periodId);
    }

    /**
     * 查询学段列表
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Result getPeriodList(){
        return periodService.searchPeriodList();
    }

    /**
     * 根据学段id查询学段
     * @return
     */
    @RequestMapping(value = "getPeriodById",method = RequestMethod.GET)
    public Result getPeriodById(int periodId){
        return periodService.getPeriodById(periodId);
    }

    /**
     * 保存学校
     * @param schoolEntity
     * @return
     */
    @RequestMapping(value = "addSchool",method = RequestMethod.POST)
    public Result addSchool(@RequestBody SchoolEntity schoolEntity){
        return schoolService.addSchool(schoolEntity);
    }

    /**
     * 修改学校信息
     * @param schoolEntity
     * @return
     */
    @RequestMapping(value = "updateSchool",method = RequestMethod.POST)
    public Result updateSchool(@RequestBody SchoolEntity schoolEntity){
        return schoolService.updateSchool(schoolEntity);
    }


    /**
     * 查询学校信息
     * @return
     */
    @RequestMapping(value = "searchSchool",method = RequestMethod.GET)
    public Result searchSchool(){
        return schoolService.searchSchool();
    }

    /**
     * 根据学校查询学校信息
     * @param  schoolId 学校id
     * @return
     */
    @RequestMapping(value = "searchSchoolById",method = RequestMethod.GET)
    public Result searchSchoolById(int schoolId){
        return schoolService.searchSchoolById(schoolId);
    }
}
