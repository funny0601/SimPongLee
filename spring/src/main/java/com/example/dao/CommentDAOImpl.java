package com.example.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentDAOImpl implements CommentDAO {

	@Inject
	private SqlSession sqlSession;
	
	private static final String Namespace = "com.example.mapper.commentMapper";

	@Override
	public List selectComment(int categoryid, String selfDiagnosisLevel) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryid", categoryid);
		map.put("selfDiagnosisLevel", selfDiagnosisLevel);
		return sqlSession.selectList(Namespace+".selectComment", map);
	}
}
