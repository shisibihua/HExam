package com.library.bexam.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author JayChen
 */
public class PaperEntity {

    private String paperId;
    private String paperTitle;
    private String paperSubTitle;
    private String typeId;
    private String subjectId;
    private String paperTime;
    private String userId;
    private String paperExtend;
    private List<PaperContentEntity> paperContent;
    private String createTime;
    private String updateTime;

    public PaperEntity() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = simpleDateFormat.format(new Date());
        this.updateTime = simpleDateFormat.format(new Date());
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getPaperSubTitle() {
        return paperSubTitle;
    }

    public void setPaperSubTitle(String paperSubTitle) {
        this.paperSubTitle = paperSubTitle;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getPaperTime() {
        return paperTime;
    }

    public void setPaperTime(String paperTime) {
        this.paperTime = paperTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaperExtend() {
        return paperExtend;
    }

    public void setPaperExtend(String paperExtend) {
        this.paperExtend = paperExtend;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<PaperContentEntity> getPaperContent() {
        return paperContent;
    }

    public void setPaperContent(List<PaperContentEntity> paperContent) {
        this.paperContent = paperContent;
    }
}
