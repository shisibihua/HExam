package com.library.bexam.form;

/**
 * 学段实体
 * @author caoqian
 * @date 20181213
 */
public class PeriodForm {
    private int periodId;
    private String periodName;
    private int schoolId;

    public PeriodForm() {
    }

    public PeriodForm(int periodId, String periodName, int schoolId) {
        this.periodId = periodId;
        this.periodName = periodName;
        this.schoolId=schoolId;
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


    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

}
