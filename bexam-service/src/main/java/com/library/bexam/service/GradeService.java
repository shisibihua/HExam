package com.library.bexam.service;
import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.GradeEntity;

import java.util.Map;

/**
 * 年级业务处理层
 * @author  caoqian
 * @date 20181216
 */
public interface GradeService {

    /**
     * 添加年级
     * @param gradeEntity
     * @return
     */
    Result addGrade(GradeEntity gradeEntity);

    /**
     * 删除年级
     * @param gradeEntity
     * @return
     */
    Result deleteGrade(Map<String,String> gradeEntity);

    /**
     * 修改年级信息
     * @param gradeEntity
     * @return
     */
    Result updateGrade(GradeEntity gradeEntity);

    /**
     * 根据学段id查询年级信息，年级id为空时查询所有年级列表
     * @param periodId  学段id
     * @return
     */
    Result searchGradeListByPeriodId(int periodId);

    /**
     * 根据年级id查询年级信息
     * @param gradeId
     * @return
     */
    Result searchGradeById(int gradeId);
}
