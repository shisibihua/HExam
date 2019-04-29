package com.library.bexam.form;

/**
 * 用户实体
 * @author caoqian
 * @date  20181213
 */
public class UserForm {
    private int userId;
    //年龄
    private int age;
    //性别,0:男;1:女
    private int gender;
    //用户类型,0:超级管理员;1:管理员;2:老师
    private int type;
    //删除状态,0：未删除，1：已删除
    private int status;
    //科目
    private int subjectId;
    //科目名称
    private String subjectName;
    //用户名
    private String userName;
    //真实姓名
    private String userRealName;
    //用户密码
    private String userPwd;
    //手机号
    private String mobile;
    //头像地址
    private String headPath;
    //用户token
    private String token;
    //创建时间
    private String createTime;

    public UserForm() {
    }

    public UserForm(int userId, int age, int gender, int type, int status , int subjectId , String subjectName, String userName,
                    String userRealName, String userPwd, String mobile, String headPath, String token, String createTime) {
        this.userId = userId;
        this.age = age;
        this.gender = gender;
        this.type = type;
        this.status = status;
        this.subjectId=subjectId;
        this.subjectName=subjectName;
        this.userName = userName;
        this.userRealName = userRealName;
        this.userPwd = userPwd;
        this.mobile = mobile;
        this.headPath = headPath;
        this.token = token;
        this.createTime = createTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
