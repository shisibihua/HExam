package com.library.bexam.dao;

import com.library.bexam.entity.PointEntity;
import com.library.bexam.form.PointForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识点操作数据库类
 * @author caoqian
 * @date 20181222
 */
public interface PointDao {
    /**
     * 添加知识点
     * @param pointEntity
     * @return
     */
    boolean addPoint(PointEntity pointEntity);

    /**
     * 批量保存知识点
     * @param pointEntityList
     */
    boolean addPointList(List<PointForm> pointEntityList);

    /**
     * 保存科目与知识点的关系
     * @param pointEntityList
     * @return
     */
    boolean addSubject2Point(List<PointForm> pointEntityList);

    /**
     * 根据学段ids获取知识点列表
     * @param periodIdArr 学段ids
     * @return
     */
    List<PointEntity> searchPointListByPeriodIds(@Param("periodIdArr")String[] periodIdArr);

    /**
     * 根据科目id获取知识点列表
     * @param subjectId 科目id
     * @return
     */
    List<PointEntity>  searchPointListBySubId(@Param("subjectId") int subjectId);

    /**
     * 根据父级知识点查询子级知识点
     * @param pointList
     * @return
     */
    List<PointEntity> searchChildPointList(List<PointEntity> pointList);
}
