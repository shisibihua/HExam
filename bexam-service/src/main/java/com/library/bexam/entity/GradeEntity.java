package com.library.bexam.entity;

import com.library.bexam.form.ClassForm;
import com.library.bexam.form.PeriodForm;

import java.util.List;

/**
 * 年级实体
 * @author caoqian
 * @date 20181213
 */
public class GradeEntity {
    private int gradeId;
    private String gradeName;
    private int periodId;
    private String periodName;
    private PeriodForm periodEntity;
    private List<ClassForm> classEntityList;
    private String createTime;

    public GradeEntity() {
    }

    public GradeEntity(int gradeId, String gradeName, int periodId, String periodName, List<ClassForm> classEntityList, String createTime) {
        this.gradeId = gradeId;
        this.gradeName = gradeName;
        this.periodId=periodId;
        this.periodName=periodName;
        this.classEntityList = classEntityList;
        this.createTime = createTime;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
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

    public List<ClassForm> getClassEntityList() {
        return classEntityList;
    }

    public void setClassEntityList(List<ClassForm> classEntityList) {
        this.classEntityList = classEntityList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public PeriodForm getPeriodEntity() {
        return periodEntity;
    }

    public void setPeriodEntity(PeriodForm periodEntity) {
        this.periodEntity = periodEntity;
    }
}
