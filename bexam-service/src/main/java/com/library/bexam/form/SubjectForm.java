package com.library.bexam.form;

/**
 * 科目实体
 * @author  caoqian
 * @date 20181213
 */
public class SubjectForm {
    private int subjectId;
    //科目名称
    private String subjectName;

    public SubjectForm() {
    }

    public SubjectForm(int subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
