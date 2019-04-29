package com.library.bexam.dao;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.GradeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 年级操作数据库类
 * @author caoqian
 * @date 20181213
 */
public interface GradeDao {

    /**
     * 保存年级
     * @param gradeEntity
     * @return
     */
    boolean addGrade(GradeEntity gradeEntity);

    /**
     * 根据学段ids删除年级信息
     * @param periodIdArr 学段ids
     * @return
     */
    boolean deleteGradeByPeriodIds(@Param("periodIdArr") String[] periodIdArr);

    /**
     * 根据年级ids删除年级信息
     * @param gradeIdsArr 年级ids
     * @return
     */
    boolean deleteGradeByIds(@Param("gradeIdsArr") String[] gradeIdsArr);

    /**
     * 修改年级
     * @param gradeEntity
     * @return
     */
    boolean updateGrade(GradeEntity gradeEntity);

    /**
     * 根据学段id查询年级信息，年级id为空时查询所有年级列表
     * @param periodId  学段id
     * @return
     */
    List<GradeEntity> searchGradeListByPeriodId(@Param("periodId") int periodId);

    /**
     * 根据年级id查询年级信息
     * @param gradeId
     * @return
     */
    GradeEntity searchGradeById(@Param("gradeId") int gradeId);
}
