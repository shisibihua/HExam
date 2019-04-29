package com.library.bexam.entity;

import com.library.bexam.form.PeriodForm;
import com.library.bexam.form.VersionForm;

import java.util.List;

/**
 * 科目实体
 * @author  caoqian
 * @date 20181213
 */
public class SubjectEntity {
    private int subjectId;
    //科目名称
    private String subjectName;
    //学段id
    private int periodId;
    //学段名称
    private String periodName;
    private PeriodForm periodEntity;
    //教材版本
    private List<VersionForm> versionFormList;
    //创建时间
    private String createTime;

    public SubjectEntity() {
    }

    public SubjectEntity(int subjectId, String subjectName,int periodId,String periodName, String createTime) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.periodId=periodId;
        this.periodName=periodName;
        this.createTime = createTime;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getPeriodId() {
        return periodId;
    }

    public void setPeriodId(int periodId) {
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

    public List<VersionForm> getVersionFormList() {
        return versionFormList;
    }

    public void setVersionFormList(List<VersionForm> versionFormList) {
        this.versionFormList = versionFormList;
    }
}
