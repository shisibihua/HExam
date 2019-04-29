package com.library.bexam.entity;

import com.library.bexam.form.NodeBookForm;
import com.library.bexam.form.VersionForm;

import java.util.List;

/**
 * 教材册别实体
 * @author caoqian
 * @date 20181219
 */
public class TextBookEntity {
    private int textBookId;
    private String textBookName;
    private int versionId;
    private String versionName;
    private VersionForm versionEntity;
    private List<NodeBookForm> nodeBookEntityList;
    private String createTime;

    public TextBookEntity() {
    }

    public TextBookEntity(int textBookId, String textBookName, int versionId, String versionName, String createTime) {
        this.textBookId = textBookId;
        this.textBookName = textBookName;
        this.versionId = versionId;
        this.versionName = versionName;
        this.createTime = createTime;
    }

    public int getTextBookId() {
        return textBookId;
    }

    public void setTextBookId(int textBookId) {
        this.textBookId = textBookId;
    }

    public String getTextBookName() {
        return textBookName;
    }

    public void setTextBookName(String textBookName) {
        this.textBookName = textBookName;
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

    public VersionForm getVersionEntity() {
        return versionEntity;
    }

    public void setVersionEntity(VersionForm versionEntity) {
        this.versionEntity = versionEntity;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<NodeBookForm> getNodeBookEntityList() {
        return nodeBookEntityList;
    }

    public void setNodeBookEntityList(List<NodeBookForm> nodeBookEntityList) {
        this.nodeBookEntityList = nodeBookEntityList;
    }
}
