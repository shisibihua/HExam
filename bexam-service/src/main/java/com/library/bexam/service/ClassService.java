package com.library.bexam.service;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.ClassEntity;

import java.util.Map;

/**
 * 班级业务处理层
 * @author  caoqian
 * @date 20181216
 */
public interface ClassService {
    /**
     * 添加班级
     * @param classEntity
     * @return
     */
    Result addClass(ClassEntity classEntity);

    /**
     * 根据班级id删除班级
     * @param classEntity
     * @return
     */
    Result deleteClass(Map<String, Integer> classEntity);

    /**
     * 修改班级信息
     * @param classEntity
     * @return
     */
    Result updateClass(ClassEntity classEntity);
    /**
     * 根据班级id查询班级信息
     * @param classId  班级id
     * @return
     */
    Result searchClassById(int classId);

    /**
     * 查询班级列表
     * @param gradeId  为空时，查询全部
     * @param search   可为空，关键字
     * @return
     */
    Result searchClassList(int gradeId, String search);

    /**
     * 根据用户id查询班级
     * @param userId
     * @return
     */
    Result searchClassesByUserId(int userId);

    /**
     * 给用户分配班级
     * @param params
     * @return
     */
    Result allotClassToUser(Map<String, Object> params);
}
