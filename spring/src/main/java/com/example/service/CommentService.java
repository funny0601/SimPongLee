package com.example.service;

import java.util.List;

public interface CommentService {
	public List selectComment(int categoryid, String selfDiagnosisLevel) throws Exception;
}
