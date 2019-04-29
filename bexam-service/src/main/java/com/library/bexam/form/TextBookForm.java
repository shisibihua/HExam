package com.library.bexam.form;

import com.library.bexam.entity.NodeBookEntity;

import java.util.List;

/**
 * 教材册别实体
 * @author caoqian
 * @date 20181219
 */
public class TextBookForm {
    private int textBookId;
    private String textBookName;
    private String createTime;
    private List<NodeBookForm> nodeBookEntityList;

    public TextBookForm() {
    }

    public TextBookForm(int textBookId, String textBookName, String createTime) {
        this.textBookId = textBookId;
        this.textBookName = textBookName;
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
