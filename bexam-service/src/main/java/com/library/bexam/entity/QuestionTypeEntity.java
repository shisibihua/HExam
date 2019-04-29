package com.library.bexam.entity;

import com.library.bexam.form.PeriodForm;
import com.library.bexam.form.SubjectForm;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 试题类型实体
 *
 * @author caoqian
 * @date 20181222
 */
public class QuestionTypeEntity {
    private String typeId;
    private String typeName;
    private String subjectId;
    private String subjectName;
    private String periodId;
    private String periodName;
    private PeriodForm periodEntity;
    private SubjectForm subjectEntity;
    private String createTime;

    public QuestionTypeEntity() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = simpleDateFormat.format(new Date());
    }

    public QuestionTypeEntity(String typeName) {
        this.typeName = typeName;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = simpleDateFormat.format(new Date());
    }

    public QuestionTypeEntity(String typeId, String typeName, String createTime) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.createTime = createTime;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public SubjectForm getSubjectEntity() {
        return subjectEntity;
    }

    public void setSubjectEntity(SubjectForm subjectEntity) {
        this.subjectEntity = subjectEntity;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public PeriodForm getPeriodEntity() {
        return periodEntity;
    }

    public void setPeriodEntity(PeriodForm periodEntity) {
        this.periodEntity = periodEntity;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


}
