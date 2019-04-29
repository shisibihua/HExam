package com.library.bexam.dao;

import com.library.bexam.entity.PeriodEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 学段操作数据库类
 * @author caoqian
 * @date 20181213
 */
public interface PeriodDao {

    /**
     * 保存学段信息
     * @param period
     * @return
     */
    boolean addPeriod(PeriodEntity period);

    /**
     * 根据学段ids删除学段，支持批量删除，多个","分割
     * @param periodIdArr  学段ids
     * @return
     */
    boolean deletePeriodByIds(@Param("periodIdArr")String[] periodIdArr);

    /**
     * 修改学段信息
     * @param period
     * @return
     */
    boolean updatePeriod(PeriodEntity period);

    /**
     * 获取所有学段列表
     * @return
     */
    List<PeriodEntity> searchPeriodList();

    /**
     * 根据学段id查询学段信息
     * @param periodId  学段id
     * @return
     */
    PeriodEntity searchPeriodById(int periodId);
}
