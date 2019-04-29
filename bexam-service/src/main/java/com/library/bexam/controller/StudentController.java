package com.library.bexam.controller;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.ClassEntity;
import com.library.bexam.entity.StudentEntity;
import com.library.bexam.service.ClassService;
import com.library.bexam.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 学生处理表现层
 * @author caoqina
 * @date 20181216
 */
@CrossOrigin
@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * 添加学生
     * @param studentEntity
     * @return
     */
    @RequestMapping(value="add",method = RequestMethod.POST)
    public Result addStudent(@RequestBody StudentEntity studentEntity){
        return studentService.addStudent(studentEntity);
    }

    /**
     * 根据学生ids删除学生
     * @param studentEntity
     * @return
     */
    @RequestMapping(value="delete",method = RequestMethod.POST)
    public Result deleteStudent(@RequestBody Map<String,String> studentEntity){
        return studentService.deleteStudent(studentEntity);
    }

    /**
     * 修改学生
     * @param studentEntity
     * @return
     */
    @RequestMapping(value="update",method = RequestMethod.POST)
    public Result updateStudent(@RequestBody StudentEntity studentEntity){
        return studentService.updateStudent(studentEntity);
    }

    /**
     * 根据学生id查询学生信息
     * @param studentId  学生id
     * @return
     */
    @RequestMapping(value="getStudentById",method = RequestMethod.GET)
    public Result getStudentById(int studentId){
        return studentService.searchStudentById(studentId);
    }

    /**
     * 查询学生列表
     * @param classId  班级id,为0时查询全部学生
     * @param search   可为空，关键字，学生名
     * @return
     */
    @RequestMapping(value="list",method = RequestMethod.GET)
    public Result searchClassList(int classId,String search){
        return studentService.searchStudentList(classId,search);
    }

    /**
     * 分页查询学生列表
     * @param classId  班级id,为0时查询全部学生
     * @param search   可为空，关键字，学生名
     * @param page 当前页
     * @param pageSize  每页条数
     * @return
     */
    @RequestMapping(value="searchPageList",method = RequestMethod.GET)
    public Result searchPageList(int classId,String search,int page,int pageSize){
        return studentService.searchPageList(classId,search,page,pageSize);
    }

    /**
     * 获取机构树
     * @return
     */
    @RequestMapping(value="orgTree",method = RequestMethod.GET)
    public Result orgTree(){
        return studentService.searchOrgTree();
    }
}
