package com.example.dto;

public class CommentVO {
    private int categoryid;
    private String selfDiagnosisLevel;
    private String levelComment;

    public int getCategoryid() { return categoryid; }
    public String getSelfDiagnosisLevel() { return selfDiagnosisLevel; }
    public String getLevelComment() { return levelComment; }

    public void setCategoryid(int categoryid) { this.categoryid = categoryid; }
    public void setSelfDiagnosisLevel(String selfDiagnosisLevel) { this.selfDiagnosisLevel = selfDiagnosisLevel; }
    public void setLevelComment(String levelComment) { this.levelComment = levelComment; }
}
