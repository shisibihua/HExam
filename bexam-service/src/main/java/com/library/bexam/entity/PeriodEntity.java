package com.library.bexam.entity;

import com.library.bexam.form.GradeForm;
import com.library.bexam.form.SchoolForm;
import com.library.bexam.form.SubjectForm;

import java.util.List;

/**
 * 学段实体
 * @author caoqian
 * @date 20181213
 */
public class PeriodEntity {
    private int periodId;
    private String periodName;
    private int schoolId;
    private String schoolName;
    private SchoolForm schoolEntity;
    private List<GradeForm> gradeList;
    private List<SubjectForm> subjectList;
    private String createTime;

    public PeriodEntity() {
    }

    public PeriodEntity(int periodId, String periodName, int schoolId, String schoolName , List<GradeForm> gradeList, String createTime) {
        this.periodId = periodId;
        this.periodName = periodName;
        this.gradeList = gradeList;
        this.schoolId=schoolId;
        this.schoolName=schoolName;
        this.createTime = createTime;
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

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public List<GradeForm> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<GradeForm> gradeList) {
        this.gradeList = gradeList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public SchoolForm getSchoolEntity() {
        return schoolEntity;
    }

    public void setSchoolEntity(SchoolForm schoolEntity) {
        this.schoolEntity = schoolEntity;
    }

    public List<SubjectForm> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<SubjectForm> subjectList) {
        this.subjectList = subjectList;
    }
}
