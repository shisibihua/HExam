package com.library.bexam.dao;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.ClassEntity;
import com.library.bexam.form.ClassForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 班级操作数据库类
 * @author caoqian
 * @date 20181213
 */
public interface ClassDao {

    /**
     * 添加班级信息
     * @param classEntity
     * @return
     */
    boolean addClass(ClassEntity classEntity);
    /**
     * 根据学段ids查询班级信息
     * @param periodIdArr 学段ids
     * @return
     */
    List<ClassEntity> searchClassListByPeriodIds(@Param("periodIdArr") String[] periodIdArr);
    /**
     * 根据学段ids删除班级信息
     * @param periodIdArr  学段ids
     * @return
     */
    boolean deleteClassByPeriodIds(@Param("periodIdArr") String[] periodIdArr);

    /**
     * 根据班级ids删除学生与班级关系
     * @param classList  班级集合
     * @return
     */
    boolean deleteClass2Student(List<ClassEntity> classList);

    /**
     * 根据班级ids删除班级信息
     * @param classList
     * @return
     */
    boolean deleteClassByClassIds(List<ClassEntity> classList);
    /**
     * 根据年级ids查询班级信息
     * @param gradeIds  年级ids
     * @return
     */
    List<ClassEntity> searchClassListByGradeIds(@Param("gradeIds") String[] gradeIds);

    /**
     * 修改班级信息
     * @param classEntity
     * @return
     */
    boolean updateClass(ClassEntity classEntity);

    /**
     * 根据班级id查询班级信息
     * @param classId  班级id
     * @return
     */
    ClassEntity searchClassById(@Param("classId") int classId);

    /**
     * 根据年级id查询班级信息，为空时查询全部班级
     * @param gradeId  年级id
     * @param search   关键字
     * @return
     */
    List<ClassEntity> searchClassList(@Param("gradeId") int gradeId,@Param("search") String search);

    /**
     *
     * @param userId
     * @return
     */
    List<ClassEntity> searchClassesByUserId(@Param("userId") int userId);

    /**
     * 分配班级给用户
     * @param classFormList
     * @param userId
     * @return
     */
    boolean allotClassToUser(@Param("list") List<ClassForm> classFormList,@Param("userId") int userId);
}
