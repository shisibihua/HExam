package com.library.bexam.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 试题难易度实体
 *
 * @author JayChen
 */
public class QuestionDifficultEntity {
    private int difficultId;
    private String difficultName;
    private String createTime;
    private String updateTime;

    public QuestionDifficultEntity() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = simpleDateFormat.format(new Date());
    }

    public QuestionDifficultEntity(String difficultName) {
        this.difficultName = difficultName;
    }

    public int getDifficultId() {
        return difficultId;
    }

    public void setDifficultId(int difficultId) {
        this.difficultId = difficultId;
    }

    public String getDifficultName() {
        return difficultName;
    }

    public void setDifficultName(String difficultName) {
        this.difficultName = difficultName;
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
}
