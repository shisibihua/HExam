package com.library.bexam.entity;


import com.library.bexam.form.GradeForm;
import com.library.bexam.form.PeriodForm;
import com.library.bexam.form.StudentForm;
import com.library.bexam.form.UserForm;
import org.apache.shiro.web.filter.authc.UserFilter;

import java.util.List;

/**
 * 班级实体
 * @author  caoqian
 * @date 20181213
 */
public class ClassEntity {
    private int classId;
    private String className;
    private int gradeId;
    private String gradeName;
    private GradeForm gradeEntity;
    private List<StudentForm> studentEntityList;
    private int periodId;
    private String periodName;
    private PeriodForm periodEntity;
    private List<UserForm> userEntityList;
    private String createTime;

    public ClassEntity() {
    }

    public ClassEntity(int classId, String className, int gradeId, String gradeName, List<StudentForm> studentEntityList, String createTime) {
        this.classId = classId;
        this.className = className;
        this.gradeId=gradeId;
        this.gradeName=gradeName;
        this.studentEntityList = studentEntityList;
        this.createTime = createTime;
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

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public List<StudentForm> getStudentEntityList() {
        return studentEntityList;
    }

    public void setStudentEntityList(List<StudentForm> studentEntityList) {
        this.studentEntityList = studentEntityList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public GradeForm getGradeEntity() {
        return gradeEntity;
    }

    public void setGradeEntity(GradeForm gradeEntity) {
        this.gradeEntity = gradeEntity;
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

    public List<UserForm> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserForm> userEntityList) {
        this.userEntityList = userEntityList;
    }

    public void setPeriodEntity(PeriodForm periodEntity) {
        this.periodEntity = periodEntity;
    }
}
