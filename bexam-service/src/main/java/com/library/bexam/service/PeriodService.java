package com.library.bexam.service;
import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.PeriodEntity;

import java.util.Map;

/**
 * 学段业务处理层
 * @author  caoqian
 * @date 20181215
 */
public interface PeriodService {
    /**
     * 添加学段
     * @param periodEntity
     * @return
     */
    Result addPeriod(PeriodEntity periodEntity);

    /**
     * 修改学段
     * @param periodEntity
     * @return
     */
    Result updatePeriod(PeriodEntity periodEntity);

    /**
     * 删除学段
     * @param periodEntity
     * @return
     */
    Result deletePeriodById(Map<String,String> periodEntity);

    /**
     * 查询学段列表
     * @return
     */
    Result searchPeriodList();

    /**
     * 根据学段id查询学段
     * @return
     */
    Result getPeriodById(int periodId);
}
