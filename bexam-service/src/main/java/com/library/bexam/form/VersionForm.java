package com.library.bexam.form;


import com.library.bexam.common.util.DateUtil;

import java.util.List;

/**
 * 教材版本实体
 * @author caoqian
 * @date  20181219
 */
public class VersionForm {
    private int versionId;
    private String versionName;
    private String createTime;
    private List<TextBookForm> textBookEntityList;
    public VersionForm() {
    }

    public VersionForm(int versionId, String versionName, String createTime) {
        this.versionId = versionId;
        this.versionName = versionName;
        this.createTime = createTime;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
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
