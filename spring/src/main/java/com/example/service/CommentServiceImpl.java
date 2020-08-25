package com.example.service;

import com.example.dao.CommentDAO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

	@Inject
	private CommentDAO dao;

	@Override
	public List selectComment(int categoryid, String selfDiagnosisLevel) throws Exception {
		return dao.selectComment(categoryid, selfDiagnosisLevel);
	}
}