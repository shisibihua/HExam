package com.library.bexam.service;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.StudentEntity;

import java.util.Map;

/**
 * 学生业务处理层
 * @author  caoqian
 * @date 20181216
 */
public interface StudentService {
    /**
     * 添加学生
     * @param studentEntity
     * @return
     */
    Result addStudent(StudentEntity studentEntity);

    /**
     * 根据学生ids删除学生
     * @param studentEntity
     * @return
     */
    Result deleteStudent(Map<String, String> studentEntity);

    /**
     * 修改学生
     * @param studentEntity
     * @return
     */
    Result updateStudent(StudentEntity studentEntity);

    /**
     * 根据学生id查询学生信息
     * @param studentId  学生id
     * @return
     */
    Result searchStudentById(int studentId);

    /**
     * 查询学生列表
     * @param classId  班级id
     * @param search   可为空，关键字，学生名
     * @return
     */
    Result searchStudentList(int classId, String search);

    /**
     * 分页查询学生列表
     * @param classId  班级id
     * @param search   可为空，关键字，学生名
     * @param page 当前页
     * @param pageSize  每页条数
     * @return
     */
    Result searchPageList(int classId, String search, int page, int pageSize);

    /**
     * 获取机构树
     * @return
     */
    Result searchOrgTree();
}
