package com.library.bexam.form;

import java.util.List;

/**
 * 组织机构实体
 * @author caoqian
 * @date 20181220
 */
public class OrgForm {
    //机构id
    private  int  id;
    //机构名称
    private String name;
    //机构类型
    private int type;

    private String tag;
    private List<OrgForm> children;

    public OrgForm() {
    }

    public OrgForm(int id, String name, int type, List<OrgForm> children) {
        this.id = id;
        this.name = name;
        this.type=type;
        this.children = children;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<OrgForm> getChildren() {
        return children;
    }

    public void setChildren(List<OrgForm> children) {
        this.children = children;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
