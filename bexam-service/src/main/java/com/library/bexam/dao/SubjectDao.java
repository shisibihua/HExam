package com.library.bexam.dao;

import com.library.bexam.entity.SubjectEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科目操作数据库类
 * @author caoqian
 * @date 20181213
 */
public interface SubjectDao {
    /**
     * 保存科目
     * @param subject
     * @return
     */
    boolean addSubject(SubjectEntity subject);

    /**
     * 批量保存科目
     * @param subjectList
     * @return
     */
    boolean addSubjectList(List<SubjectEntity> subjectList);

    /**
     * 保存学段与科目的关系
     * @param subjectList
     * @return
     */
    boolean addPeriod2Subject(List<SubjectEntity> subjectList);

    /**
     * 根据科目ids删除科目信息
     * @param subjectIdArr  科目id,多个","分割
     * @return
     */
    boolean deleteSubjectById(@Param("subjectIdArr") String[] subjectIdArr);

    /**
     * 根据学段id删除学段与科目的关系
     * @param periodIdArr  学段ids
     * @return
     */
    boolean deletePeriod2SubjectByPeriodIds(@Param("periodIdArr") String[] periodIdArr);

    /**
     * 根据科目ids删除科目与学段的关系
     * @param subjectIdArr  科目id,多个","分割
     * @return
     */
    boolean deletePeriod2SubjectBySubjectIds(@Param("subjectIdArr") String[] subjectIdArr);

    /**
     * 修改科目信息
     * @param subject
     * @return
     */
    boolean updateSubject(SubjectEntity subject);

    /**
     * 根据科目id查询科目信息
     * @param subjectId  科目id
     * @return
     */
    SubjectEntity searchSubjectById(int subjectId);

    /**
     * 获取学科列表
     * @param periodId 学段id
     * @return
     */
    List<SubjectEntity> searchSubjectListByPeriodId(@Param("periodId") int periodId);

    /**
     * 查询所有科目列表
     * @return
     */
    List<SubjectEntity> searchSubjectList();

    List<SubjectEntity> searchExamList(@Param("subjectId") int subjectId);
}
