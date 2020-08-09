package com.example.service;

import java.util.List;

public interface SelfDiagnosisService {
	public List selectQuestion(String question, int questionid) throws Exception;
}
