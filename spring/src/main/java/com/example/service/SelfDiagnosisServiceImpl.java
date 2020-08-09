package com.example.service;

import com.example.dao.CalendarDAO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SelfDiagnosisServiceImpl implements SelfDiagnosisService {

	@Inject
	private CalendarDAO dao;

	@Override
	public List selectQuestion(String question, int questionid) throws Exception {
		return dao.selectDiary(question, questionid);
	}

}