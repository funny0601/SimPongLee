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

}