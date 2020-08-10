package com.example.dto;

public class SelfDiagnosisVO {
    private String question;
    private int questionid;

    public String getQuestion() {
        return question;
    }

    public int getQuestionid() { return questionid; }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }
}
