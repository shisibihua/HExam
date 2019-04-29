package com.library.bexam.form;

import java.util.List;

/**
 * 教材章节实体
 * @author caoqian
 * @date 20181219
 */
public class NodeBookForm {
    private int nodeId;
    private String nodeName;
    private int parentId;
    private List<NodeBookForm> nodeList;
    private int textBookId;
    private String createTime;

    public NodeBookForm() {
    }

    public NodeBookForm(int nodeId, String nodeName, int parentId, String createTime) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.parentId = parentId;
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

    public List<NodeBookForm> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<NodeBookForm> nodeList) {
        this.nodeList = nodeList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getTextBookId() {
        return textBookId;
    }

    public void setTextBookId(int textBookId) {
        this.textBookId = textBookId;
    }
}
