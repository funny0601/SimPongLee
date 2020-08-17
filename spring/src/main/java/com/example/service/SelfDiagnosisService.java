package com.example.service;

import com.example.dto.SelfDiagnosisVO;

import java.util.List;

public interface SelfDiagnosisService {
	public List selectQuestion(int categoryid) throws Exception;
	public List selectLevel(int userid, int categoryid) throws Exception;
//	public List selectUserScoreExists(int userid, int categoryid) throws Exception;
	public int insertDiagnosisResult(int userid, int categoryid, int selfDiagnosisScore, int selfDiagnosisLevel) throws Exception;
}
