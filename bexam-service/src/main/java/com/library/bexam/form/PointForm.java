package com.library.bexam.form;

import java.util.List;

/**
 * 知识点实体
 * @author caoqian
 * @date 20181222
 */
public class PointForm {
    private int pointId;
    private String pointName;
    private int parentId;
    private List<PointForm> pointList;
    private String createTime;
    private int subjectId;

    public PointForm() {
    }

    public PointForm(int pointId, String pointName, int parentId, List<PointForm> pointList, String createTime) {
        this.pointId = pointId;
        this.pointName = pointName;
        this.parentId=parentId;
        this.pointList = pointList;
        this.createTime = createTime;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<PointForm> getPointList() {
        return pointList;
    }

    public void setPointList(List<PointForm> pointList) {
        this.pointList = pointList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
