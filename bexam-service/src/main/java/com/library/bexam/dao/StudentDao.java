package com.library.bexam.dao;

import com.library.bexam.entity.SchoolEntity;
import com.library.bexam.entity.StudentEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生操作数据库类
 * @author caoqian
 * @date 20181213
 */
public interface StudentDao {
    /**
     * 保存学生
     * @param studentEntity
     * @return
     */
    int addStudent(StudentEntity studentEntity);

    /**
     * 根据学生ids删除学生
     * @param studentIdsArr
     * @return
     */
    boolean deleteStudentByIds(@Param("studentIdsArr") String[] studentIdsArr);

    /**
     * 根据学生ids删除学生与班级关系
     * @param studentIdsArr
     * @return
     */
    boolean deleteClass2Student(@Param("studentIdsArr") String[] studentIdsArr);

    /**
     * 修改学生信息
     * @param studentEntity
     * @return
     */
    boolean updateStudent(StudentEntity studentEntity);

    /**
     * 根据学生id查询学生信息
     * @param studentId  学生id
     * @return
     */
    StudentEntity searchStudentById(@Param("studentId") int studentId);

    /**
     * 根据班级id查询学生信息
     * @param classId  班级id
     * @param search   可为空，关键字，学生名
     * @return
     */
    List<StudentEntity> searchStudentListByClassId(@Param("classId") int classId,@Param("search") String search);

    /**
     * 保存学生与班级的关系
     * @param classId
     * @param stuId
     * @return
     */
    boolean addClass2Student(@Param("classId") int classId,@Param("stuId") int stuId);

    /**
     * 查询学生总数
     * @return
     */
    int getTotalCount();

    /**
     * 分页查询学生信息
     * @param classId  班级id
     * @param search   关键字，学生名
     * @param firstIndex  起始页
     * @param pageSize    每页条数
     * @return
     */
    List<StudentEntity> searchStudentByPage(@Param("classId") int classId, @Param("search") String search,
                                            @Param("firstIndex") int firstIndex,@Param("pageSize") int pageSize);

    /**
     * 获取组织机构数
     * @return
     */
    SchoolEntity searchOrgTree();
}
