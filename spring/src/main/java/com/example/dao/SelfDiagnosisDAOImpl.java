package com.example.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SelfDiagnosisDAOImpl implements SelfDiagnosisDAO {

	@Inject
	private SqlSession sqlSession;

	private static final String Namespace = "com.example.mapper.selfDiagnosisMapper";

	@Override
	public List selectLevel(int userid, int categoryid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("categoryid", categoryid);
		return sqlSession.selectList(Namespace+".selectLevel", map);
	}

	@Override
	public List selectResult(int userid, int categoryid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("categoryid", categoryid);
		return sqlSession.selectList(Namespace+".selectResult", map);
	}

	@Override
	public int insertResult(int userid, int categoryid, int selfDiagnosisScore, String selfDiagnosisLevel) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("categoryid", categoryid);
		map.put("selfDiagnosisScore", selfDiagnosisScore);
		map.put("selfDiagnosisLevel", selfDiagnosisLevel);
		return sqlSession.insert(Namespace + ".insertResult", map);
	}

	@Override
	public int updateResult(int userid, int categoryid, int selfDiagnosisScore, String selfDiagnosisLevel) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("categoryid", categoryid);
		map.put("selfDiagnosisScore", selfDiagnosisScore);
		map.put("selfDiagnosisLevel", selfDiagnosisLevel);
		return sqlSession.update(Namespace + ".updateResult", map);
	}
}
