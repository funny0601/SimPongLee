package com.ddukddak.simpunglee;
public class SelfDiagnosisCategoryVO {

    private String categoryName;
    private int score;
    private String grade;

    public SelfDiagnosisCategoryVO(String categoryName, int score, String grade){
        this.categoryName=categoryName;
        this.score=score;
        this.grade=grade;
    }

    public int getScore() {
        return score;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getGrade() {
        return grade;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setScore(int score) {
        this.score = score;
    }
}