package com.library.bexam.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 试题实体
 *
 * @author JayChen
 */
public class QuestionEntity {
    private int questionId;
    private int questionTypeId;
    private String questionTypeName;
    private int questionDifficult;
    private String questionDifficultName;
    private String questionComment;

    //一级知识点
    private int questionPointId;
    //二级知识点
    private int questionPointId2;
    //三级知识点
    private int questionPointId3;
    private String questionPointName;
    private int questionVersionId;
    private String questionVersionName;
    private int questionBookId;
    private String questionBookName;
    //一级章节
    private int questionNodeId;
    private String questionNodeName;
    //二级章节
    private int questionNodeId2;
    private String questionNodeName2;
    //三级章节
    private int questionNodeId3;
    private String questionNodeName3;
    private String questionSource;
    private String questionContent;
    private String questionOption;
    private String questionAnswer;
    private String questionAnalysis;
    private int subjectId;
    private String subjectName;
    private int usageCount;
    //是否收藏，true：未收藏；false：已收藏
    private boolean isCollection;
    //是否加入试题篮，true:已加入；false:未加入
    private boolean isAddBasket;
    //存储来源，0：网络试题（拉取的试题）；1：本地试题（本地添加的试题）
    private int storage;
    private int userId;
    //是否公开，true:公开;false:不公开,默认为不公开
    private boolean isPublic;
    private String createTime;
    private String updateTime;

    public QuestionEntity() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = simpleDateFormat.format(new Date());
        this.updateTime = simpleDateFormat.format(new Date());
    }

    public QuestionEntity(int questionTypeId, int questionDifficult, int questionPointId,int questionPointId2,
                          int questionPointId3, int questionVersionId,int questionBookId, int questionNodeId,
                          int questionNodeId2, int questionNodeId3,String questionSource, String questionContent,
                          String questionOption, String questionAnswer,String questionAnalysis, int subjectId) {
//        this.questionId = questionId;
        this.questionTypeId = questionTypeId;
        this.questionDifficult = questionDifficult;
        this.questionPointId = questionPointId;
        this.questionPointId2 = questionPointId2;
        this.questionPointId3 = questionPointId3;
        this.questionVersionId = questionVersionId;
        this.questionBookId = questionBookId;
        this.questionNodeId = questionNodeId;
        this.questionNodeId2 = questionNodeId2;
        this.questionNodeId3 = questionNodeId3;
        this.questionSource = questionSource;
        this.questionContent = questionContent;
        this.questionOption = questionOption;
        this.questionAnswer = questionAnswer;
        this.questionAnalysis = questionAnalysis;
        this.subjectId = subjectId;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = simpleDateFormat.format(new Date());
        this.updateTime = simpleDateFormat.format(new Date());
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public int getQuestionDifficult() {
        return questionDifficult;
    }

    public void setQuestionDifficult(int questionDifficult) {
        this.questionDifficult = questionDifficult;
    }

    public String getQuestionComment() {
        return questionComment;
    }

    public void setQuestionComment(String questionComment) {
        this.questionComment = questionComment;
    }

    public int getQuestionPointId() {
        return questionPointId;
    }

    public void setQuestionPointId(int questionPointId) {
        this.questionPointId = questionPointId;
    }

    public int getQuestionPointId2() {
        return questionPointId2;
    }

    public void setQuestionPointId2(int questionPointId2) {
        this.questionPointId2 = questionPointId2;
    }

    public int getQuestionPointId3() {
        return questionPointId3;
    }

    public void setQuestionPointId3(int questionPointId3) {
        this.questionPointId3 = questionPointId3;
    }

    public int getQuestionVersionId() {
        return questionVersionId;
    }

    public void setQuestionVersionId(int questionVersionId) {
        this.questionVersionId = questionVersionId;
    }

    public int getQuestionBookId() {
        return questionBookId;
    }

    public void setQuestionBookId(int questionBookId) {
        this.questionBookId = questionBookId;
    }

    public int getQuestionNodeId() {
        return questionNodeId;
    }

    public void setQuestionNodeId(int questionNodeId) {
        this.questionNodeId = questionNodeId;
    }

    public int getQuestionNodeId2() {
        return questionNodeId2;
    }

    public void setQuestionNodeId2(int questionNodeId2) {
        this.questionNodeId2 = questionNodeId2;
    }

    public int getQuestionNodeId3() {
        return questionNodeId3;
    }

    public void setQuestionNodeId3(int questionNodeId3) {
        this.questionNodeId3 = questionNodeId3;
    }

    public String getQuestionSource() {
        return questionSource;
    }

    public void setQuestionSource(String questionSource) {
        this.questionSource = questionSource;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionOption() {
        return questionOption;
    }

    public void setQuestionOption(String questionOption) {
        this.questionOption = questionOption;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getQuestionAnalysis() {
        return questionAnalysis;
    }

    public void setQuestionAnalysis(String questionAnalysis) {
        this.questionAnalysis = questionAnalysis;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
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

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getQuestionTypeName() {
        return questionTypeName;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    public String getQuestionDifficultName() {
        return questionDifficultName;
    }

    public void setQuestionDifficultName(String questionDifficultName) {
        this.questionDifficultName = questionDifficultName;
    }

    public String getQuestionPointName() {
        return questionPointName;
    }

    public void setQuestionPointName(String questionPointName) {
        this.questionPointName = questionPointName;
    }

    public String getQuestionVersionName() {
        return questionVersionName;
    }

    public void setQuestionVersionName(String questionVersionName) {
        this.questionVersionName = questionVersionName;
    }

    public String getQuestionBookName() {
        return questionBookName;
    }

    public void setQuestionBookName(String questionBookName) {
        this.questionBookName = questionBookName;
    }

    public String getQuestionNodeName() {
        return questionNodeName;
    }

    public void setQuestionNodeName(String questionNodeName) {
        this.questionNodeName = questionNodeName;
    }

    public String getQuestionNodeName2() {
        return questionNodeName2;
    }

    public void setQuestionNodeName2(String questionNodeName2) {
        this.questionNodeName2 = questionNodeName2;
    }

    public String getQuestionNodeName3() {
        return questionNodeName3;
    }

    public void setQuestionNodeName3(String questionNodeName3) {
        this.questionNodeName3 = questionNodeName3;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public boolean getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(boolean collection) {
        isCollection = collection;
    }

    public boolean getIsAddBasket() {
        return isAddBasket;
    }

    public void setIsAddBasket(boolean addBasket) {
        isAddBasket = addBasket;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
