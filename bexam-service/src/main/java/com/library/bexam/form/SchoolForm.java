package com.library.bexam.form;

/**
 * 学校实体
 * @author  caoqian
 * @date 20181217
 */
public class SchoolForm {
    private int schoolId;
    //学校名称
    private String schoolName;

    public SchoolForm() {
    }

    public SchoolForm(int schoolId, String schoolName) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
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

}
