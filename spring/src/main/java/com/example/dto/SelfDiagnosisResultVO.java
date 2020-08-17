package com.example.dto;

public class SelfDiagnosisResultVO {
    private int userid;
    private int categoryid;
    private int selfDiagnosisScore;
    private int selfDiagnosisLevel;

    public int getUserid() { return userid; }
    public int getCategoryid() { return categoryid; }
    public int getSelfDiagnosisScore() { return selfDiagnosisScore; }
    public int getSelfDiagnosisLevel() { return selfDiagnosisLevel; }

    public void setUserid(int userid) { this.userid = userid; }
    public void setCategoryid(int categoryid) { this.categoryid = categoryid; }
    public void setSelfDiagnosisScore(int selfDiagnosisScore) { this.selfDiagnosisScore = selfDiagnosisScore; }
    public void setSelfDiagnosisLevel(int selfDiagnosisLevel) { this.selfDiagnosisLevel = selfDiagnosisLevel; }
}
