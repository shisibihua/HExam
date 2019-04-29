package com.library.bexam.entity;

import com.library.bexam.form.ClassForm;

/**
 * 学生实体
 * @author  caoqian
 * @date 20181213
 */
public class StudentEntity {
    private int studentId;
    //学生真实名称
    private String realName;
    //学生编号
    private String code;
    //年龄
    private int age;
    //性别
    private int gender;
    //班级id
    private int classId;
    private ClassForm classEntity;
    //创建时间
    private String createTime;

    public StudentEntity() {
    }

    public StudentEntity(int studentId, String realName, String code, int age, int gender, int classId,String createTime) {
        this.studentId = studentId;
        this.realName = realName;
        this.code = code;
        this.age = age;
        this.gender = gender;
        this.classId = classId;
        this.createTime = createTime;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String  getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String  createTime) {
        this.createTime = createTime;
    }

    public ClassForm getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassForm classEntity) {
        this.classEntity = classEntity;
    }
}
