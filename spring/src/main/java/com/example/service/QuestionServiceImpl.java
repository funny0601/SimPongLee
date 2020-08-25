package com.example.service;

import com.example.dao.QuestionDAO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Inject
	private QuestionDAO dao;

	@Override
	public List selectQuestion(int categoryid) throws Exception {
		return dao.selectQuestion(categoryid);
	}
}