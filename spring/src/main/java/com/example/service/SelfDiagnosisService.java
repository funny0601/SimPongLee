package com.example.service;

import com.example.dto.SelfDiagnosisVO;

import java.util.List;

public interface SelfDiagnosisService {
	public List selectQuestion(int categoryid) throws Exception;
}
