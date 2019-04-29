package com.library.bexam.service;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.SchoolEntity; /**
 * 学校业务处理
 * @author caoqian
 * @date 20181217
 */
public interface SchoolService {
    /**
     * 保存学校
     * @param schoolEntity
     * @return
     */
    Result addSchool(SchoolEntity schoolEntity);

    /**
     * 修改学校信息
     * @param schoolEntity
     * @return
     */
    Result updateSchool(SchoolEntity schoolEntity);

    /**
     * 查询学校信息
     * @return
     */
    Result searchSchool();

    /**
     * 根据学校查询学校信息
     * @param  schoolId 学校id
     * @return
     */
    Result searchSchoolById(int schoolId);
}
