package com.library.bexam.form;

/**
 * 年级实体
 * @author caoqian
 * @date 20181213
 */
public class GradeForm {
    private int gradeId;
    private String gradeName;
    private int periodId;
    public GradeForm() {
    }

    public GradeForm(int gradeId, String gradeName, int periodId) {
        this.gradeId = gradeId;
        this.gradeName = gradeName;
        this.periodId=periodId;
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

}
