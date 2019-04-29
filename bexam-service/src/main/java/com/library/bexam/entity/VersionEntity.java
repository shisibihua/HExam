package com.library.bexam.entity;


import com.library.bexam.form.SubjectForm;
import com.library.bexam.form.TextBookForm;

import java.util.List;

/**
 * 教材版本实体
 * @author caoqian
 * @date  20181219
 */
public class VersionEntity {
    private int versionId;
    private String versionName;
    private int subjectId;
    private SubjectForm subjectEntity;
    //教材册别
    private List<TextBookForm> textBookEntityList;
    private String createTime;

    public VersionEntity() {
    }

    public VersionEntity(int versionId, String versionName, int subjectId, String createTime) {
        this.versionId = versionId;
        this.versionName = versionName;
        this.subjectId = subjectId;
        this.createTime = createTime;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int vesionId) {
        this.versionId = vesionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public SubjectForm getSubjectEntity() {
        return subjectEntity;
    }

    public void setSubjectEntity(SubjectForm subjectEntity) {
        this.subjectEntity = subjectEntity;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<TextBookForm> getTextBookEntityList() {
        return textBookEntityList;
    }

    public void setTextBookEntityList(List<TextBookForm> textBookEntityList) {
        this.textBookEntityList = textBookEntityList;
    }
}
