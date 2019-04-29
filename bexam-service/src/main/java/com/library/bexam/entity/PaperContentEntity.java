package com.library.bexam.entity;

import java.util.List;

/**
 * @author JayChen
 */
public class PaperContentEntity {
    private int paperContentId;
    private int contentType;
    private int score;
    private int totalScore;
    private String parentId;
    private String questionId;
    private String paperId;
    private int height;
    private List<PaperContentEntity> questions;

    public PaperContentEntity() {

    }

    public int getPaperContentId() {
        return paperContentId;
    }

    public void setPaperContentId(int paperContentId) {
        this.paperContentId = paperContentId;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<PaperContentEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<PaperContentEntity> questions) {
        this.questions = questions;
    }
}
