package com.example.dao;

import java.util.List;

public interface SelfDiagnosisDAO {
    public List selectQuestion(String question, int questionid) throws Exception;
}