package com.library.bexam.form;

/**
 * 班级实体
 * @author  caoqian
 * @date 20181213
 */
public class ClassForm {
    private int classId;
    private String className;
    private int gradeId;
    private String gradeName;
    private GradeForm gradeEntity;
    private int periodId;

    public ClassForm() {
    }

    public ClassForm(int classId, String className, int gradeId,int periodId) {
        this.classId = classId;
        this.className = className;
        this.gradeId=gradeId;
        this.periodId=periodId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getGradeId() {

        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getPeriodId() {
        return periodId;
    }

    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public GradeForm getGradeEntity() {
        return gradeEntity;
    }

    public void setGradeEntity(GradeForm gradeEntity) {
        this.gradeEntity = gradeEntity;
    }
}
