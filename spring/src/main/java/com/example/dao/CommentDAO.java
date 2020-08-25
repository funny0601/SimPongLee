package com.example.dao;

import java.util.List;

public interface CommentDAO {
    public List selectComment(int categoryid, String selfDiagnosisLevel) throws Exception;
}