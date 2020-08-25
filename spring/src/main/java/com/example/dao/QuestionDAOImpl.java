package com.example.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QuestionDAOImpl implements QuestionDAO {

	@Inject
	private SqlSession sqlSession;
	
	private static final String Namespace = "com.example.mapper.questionMapper";
	
	@Override
	public List selectQuestion(int categoryid) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryid", categoryid);
		return sqlSession.selectList(Namespace + ".selectQuestion", map);
	}
}
