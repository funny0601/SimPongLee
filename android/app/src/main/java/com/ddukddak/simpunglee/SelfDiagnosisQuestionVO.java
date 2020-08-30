package com.ddukddak.simpunglee;
class SelfDiagnosisQuestionVO {
    private String question;
    private int selectPoint;
    public SelfDiagnosisQuestionVO(String question){
        this.question=question;
        this.selectPoint= -1;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public void setSelectPoint(int selectPoint) {
        this.selectPoint = selectPoint;
    }
    public int getSelectPoint() {
        return selectPoint;
    }
    public String getQuestion() {
        return question;
    }
}