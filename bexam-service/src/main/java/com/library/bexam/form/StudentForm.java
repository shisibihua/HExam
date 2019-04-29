package com.library.bexam.form;

/**
 * 学生实体，不显示班级id
 * @author  caoqian
 * @date 20181216
 */
public class StudentForm {
    private int studentId;
    //学生真实名称
    private String realName;
    //学生编号
    private String code;
    //年龄
    private int age;
    //性别
    private int gender;

    public StudentForm() {
    }

    public StudentForm(int studentId, String realName, String code, int age, int gender) {
        this.studentId = studentId;
        this.realName = realName;
        this.code = code;
        this.age = age;
        this.gender = gender;
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

}
