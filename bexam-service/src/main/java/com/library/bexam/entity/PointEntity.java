package com.library.bexam.entity;

import com.library.bexam.form.PointForm;
import com.library.bexam.form.SubjectForm;

import java.util.List;

/**
 * 知识点实体
 * @author caoqian
 * @date 20181222
 */
public class PointEntity {
    private int pointId;
    private String pointName;
    private int parentId;
    private List<PointEntity> pointList;
    //科目id
    private int subjectId;
    private String subjectName;
    private SubjectForm subjectForm;
    private String createTime;

    public PointEntity() {
    }

    public PointEntity(int pointId, String pointName, int parentId,String createTime) {
        this.pointId = pointId;
        this.pointName = pointName;
        this.parentId=parentId;
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

    public List<PointEntity> getPointList() {
        return pointList;
    }

    public void setPointList(List<PointEntity> pointList) {
        this.pointList = pointList;
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

    public SubjectForm getSubjectForm() {
        return subjectForm;
    }

    public void setSubjectForm(SubjectForm subjectForm) {
        this.subjectForm = subjectForm;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
