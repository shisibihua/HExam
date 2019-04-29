package com.library.bexam.entity;

/**
 * @author JayChen
 */
public class PaperScoreEntity {
    private int id;
    private int paperId;
    private int studentId;
    private int paperContentId;
    private int score;
    private String answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getPaperContentId() {
        return paperContentId;
    }

    public void setPaperContentId(int paperContentId) {
        this.paperContentId = paperContentId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
