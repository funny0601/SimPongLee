package com.example.dto;

public class SelfDiagnosisVO {
    private int userid;
    private int categoryid;
    private int selfDiagnosisScore;
    private String selfDiagnosisLevel;

    public int getUserid() { return userid; }
    public int getCategoryid() { return categoryid; }
    public int getSelfDiagnosisScore() { return selfDiagnosisScore; }
    public String getSelfDiagnosisLevel() { return selfDiagnosisLevel; }

    public void setUserid(int userid) { this.userid = userid; }
    public void setCategoryid(int categoryid) { this.categoryid = categoryid; }
    public void setSelfDiagnosisScore(int selfDiagnosisScore) { this.selfDiagnosisScore = selfDiagnosisScore; }
    public void setSelfDiagnosisLevel(String selfDiagnosisLevel) { this.selfDiagnosisLevel = selfDiagnosisLevel; }
}