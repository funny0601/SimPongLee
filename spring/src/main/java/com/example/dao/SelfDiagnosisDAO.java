package com.example.dao;

import com.example.dto.SelfDiagnosisVO;

import java.util.List;

public interface SelfDiagnosisDAO {
    public List selectQuestion(int questionid) throws Exception;
}