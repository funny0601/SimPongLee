package com.example.dto;

public class QuestionVO {
    private int questionid;
    private int categoryid;
    private String question;

    public int getQuestionid() { return questionid; }
    public int getCategoryid() { return categoryid; }
    public String getQuestion() {
        return question;
    }

    public void setQuestionid(int questionid) { this.questionid = questionid; }
    public void setCategoryid(int categoryid) { this.categoryid = categoryid; }
    public void setQuestion(String question) {
        this.question = question;
    }
}
