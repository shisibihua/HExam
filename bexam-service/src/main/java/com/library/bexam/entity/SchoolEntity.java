package com.library.bexam.entity;

import com.library.bexam.form.PeriodForm;

import java.util.List;

/**
 * 学校实体
 * @author  caoqian
 * @date 20181217
 */
public class SchoolEntity {
    private int schoolId;
    //学校名称
    private String schoolName;
    //学段
    private List<PeriodForm> periodEntityList;
    private String createTime;

    public SchoolEntity() {
    }

    public SchoolEntity(int schoolId, String schoolName, List<PeriodForm> periodEntityList, String createTime) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.periodEntityList = periodEntityList;
        this.createTime = createTime;
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

    public List<PeriodForm> getPeriodEntityList() {
        return periodEntityList;
    }

    public void setPeriodEntityList(List<PeriodForm> periodEntityList) {
        this.periodEntityList = periodEntityList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
