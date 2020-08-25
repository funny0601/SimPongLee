package com.example.service;

import java.util.List;

public interface SelfDiagnosisService {
	//    public List selectLevel(int userid, int categoryid) throws Exception;
	public List selectResult(int userid, int categoryid) throws Exception;
	public int insertResult(int userid, int categoryid, int selfDiagnosisScore, String selfDiagnosisLevel) throws Exception;
	public int updateResult(int userid, int categoryid, int selfDiagnosisScore, String selfDiagnosisLevel) throws Exception;
}
