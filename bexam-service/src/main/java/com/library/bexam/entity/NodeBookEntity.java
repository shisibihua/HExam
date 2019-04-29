package com.library.bexam.entity;

import com.library.bexam.form.NodeBookForm;
import com.library.bexam.form.TextBookForm;

import java.util.List;

/**
 * 教材章节实体
 * @author caoqian
 * @date 20181219
 */
public class NodeBookEntity {
    private int nodeId;
    private String nodeName;
    private int parentId;
    private List<NodeBookEntity> nodeList;
    private int textBookId;
    private String textBookName;
    private TextBookForm textBookEntity;
    private String createTime;

    public NodeBookEntity() {
    }

    public NodeBookEntity(int nodeId, String nodeName, int parentId, int textBookId, String textBookName, String createTime) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.parentId = parentId;
        this.textBookId = textBookId;
        this.textBookName = textBookName;
        this.createTime = createTime;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<NodeBookEntity> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<NodeBookEntity> nodeList) {
        this.nodeList = nodeList;
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

    public TextBookForm getTextBookEntity() {
        return textBookEntity;
    }

    public void setTextBookEntity(TextBookForm textBookEntity) {
        this.textBookEntity = textBookEntity;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
