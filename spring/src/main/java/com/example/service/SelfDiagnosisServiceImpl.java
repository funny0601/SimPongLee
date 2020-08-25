package com.example.service;

import com.example.dao.SelfDiagnosisDAO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SelfDiagnosisServiceImpl implements SelfDiagnosisService {

	@Inject
	private SelfDiagnosisDAO dao;

	@Override
	public List selectLevel(int userid, int categoryid) throws Exception {
		return dao.selectLevel(userid, categoryid);
	}

	@Override
	public List selectResult(int userid, int categoryid) throws Exception {
		return dao.selectResult(userid, categoryid);
	}

	@Override
	public int insertResult(int userid, int categoryid, int selfDiagnosisScore, String selfDiagnosisLevel) throws Exception {
		return dao.insertResult(userid, categoryid, selfDiagnosisScore, selfDiagnosisLevel);
	}

	@Override
	public int updateResult(int userid, int categoryid, int selfDiagnosisScore, String selfDiagnosisLevel) throws Exception {
		return dao.updateResult(userid, categoryid, selfDiagnosisScore, selfDiagnosisLevel);
	}
}