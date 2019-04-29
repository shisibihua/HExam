package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.ClassEntity;
import com.library.bexam.service.ClassService;
import com.library.bexam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 班级处理表现层
 * @author caoqina
 * @date 20181216
 */
@CrossOrigin
@RestController
@RequestMapping("class")
public class ClassController {
    @Autowired
    private ClassService classService;
    @Autowired
    private UserService userService;
    /**
     * 添加班级
     * @param classEntity
     * @return
     */
    @RequestMapping(value="add",method = RequestMethod.POST)
    public Result addClass(@RequestBody ClassEntity classEntity){
        return classService.addClass(classEntity);
    }

    /**
     * 根据班级id删除班级
     * @param classEntity
     * @return
     */
    @RequestMapping(value="delete",method = RequestMethod.POST)
    public Result deleteClass(@RequestBody Map<String,Integer> classEntity){
        return classService.deleteClass(classEntity);
    }

    /**
     * 修改班级
     * @param classEntity
     * @return
     */
    @RequestMapping(value="update",method = RequestMethod.POST)
    public Result updateClass(@RequestBody ClassEntity classEntity){
        return classService.updateClass(classEntity);
    }

    /**
     * 根据班级id查询班级信息
     * @param classId  班级id
     * @return
     */
    @RequestMapping(value="getClassById",method = RequestMethod.GET)
    public Result getClassById(int classId){
        return classService.searchClassById(classId);
    }

    /**
     * 查询班级列表
     * @param gradeId  为0时，查询全部
     * @param search   可为空，关键字
     * @return
     */
    @RequestMapping(value="list",method = RequestMethod.GET)
    public Result searchClassList(int gradeId,String search){
        return classService.searchClassList(gradeId,search);
    }

    /**
     * 根据用户id查询班级
     * @param userId
     * @return
     */
    @RequestMapping(value="searchClassesByUserId",method = RequestMethod.GET)
    public Result searchClassesByUserId(int userId){
        return classService.searchClassesByUserId(userId);
    }

    /**
     * 分配用户到班级
     * @param params  {"classId":1,"userId":"1,2,3"}
     * @return
     */
    @RequestMapping(value="allotUserToClass",method = RequestMethod.POST)
    public Result allotUserToClass(@RequestBody Map<String,Object> params){
        return userService.allotUserToClass(params);
    }
}
