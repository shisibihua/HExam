package com.library.bexam.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 试卷类型实体
 *
 * @author JayChen
 */
public class PaperTypeEntity {

    private int typeId;
    private String typeName;
    private String createTime;

    public PaperTypeEntity() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = simpleDateFormat.format(new Date());
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


}
