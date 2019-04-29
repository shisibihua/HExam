package com.library.bexam.service;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.entity.SubjectEntity;
import java.util.Map;

/**
 * 科目业务处理层
 * @author  caoqian
 * @date 20181215
 */
public interface SubjectService {
    /**
     * 添加科目信息
     * @param subjectEntity
     * @return
     */
    Result addSubject(SubjectEntity subjectEntity);

    /**
     * 根据科目id查询科目信息
     * @param subjectId  科目id
     * @return
     */
    Result searchSubjectById(int subjectId);

    /**
     * 根据科目ids删除科目信息，支持批量删除
     * @param params
     * @return
     */
    Result deleteSubjectByIds(Map<String, String> params);

    /**
     * 修改科目信息
     * @param subjectEntity
     * @return
     */
    Result updateSubjectInfo(SubjectEntity subjectEntity);

    /**
     * 获取科目列表
     * @param  periodId 学段id
     * @return
     */
    Result searchSubjectList(int periodId);

    Result searchExamList(int subjectId);
}
