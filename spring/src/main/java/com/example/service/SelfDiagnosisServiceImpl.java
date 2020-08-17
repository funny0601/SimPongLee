package com.example.service;

import com.example.dao.SelfDiagnosisDAO;
import com.example.dto.SelfDiagnosisVO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SelfDiagnosisServiceImpl implements SelfDiagnosisService {

	@Inject
	private SelfDiagnosisDAO dao;

	@Override
	public List selectQuestion(int categoryid) throws Exception {
		return dao.selectQuestion(categoryid);
	}

	@Override
	public List selectLevel(int userid, int categoryid) throws Exception {
		return dao.selectLevel(userid, categoryid);
	}

//	@Override
//	public List selectUserScoreExists(int userid, int categoryid) throws Exception {
//		return dao.selectUserScoreExists(userid, categoryid);
//	}

	@Override
	public int insertDiagnosisResult(int userid, int categoryid, int selfDiagnosisScore, int selfDiagnosisLevel) throws Exception {
		return dao.insertDiagnosisResult(userid, categoryid, selfDiagnosisScore, selfDiagnosisLevel);
	}
}