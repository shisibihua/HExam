package com.library.bexam.dao;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.SchoolEntity;

/**
 * 学校数据库处理
 * @author caoqian
 * @date 20181217
 */
public interface SchoolDao {

    /**
     * 添加学校
     * @param schoolEntity
     * @return
     */
    boolean addSchool(SchoolEntity schoolEntity);

    /**
     * 修改学校信息
     * @param schoolEntity
     * @return
     */
    boolean updateSchool(SchoolEntity schoolEntity);

    /**
     * 根据学校id查询学校信息
     * @return
     */
    SchoolEntity searchSchool();

    /**
     * 根据学校名称查询学校信息
     * @param schoolName  学校名称
     * @return
     */
    SchoolEntity searchSchoolByName(String schoolName);

    /**
     * 根据学校名称查询学校信息
     * @param schoolId  学校名id
     * @return
     */
    SchoolEntity searchSchoolById(int schoolId);
}
