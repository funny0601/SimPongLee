package com.example.dao;

import com.example.dto.SelfDiagnosisVO;
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
	public List selectQuestion(int categoryid) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryid", categoryid);
		return sqlSession.selectList(Namespace + ".selectQuestion", map);
	}

	@Override
	public List selectLevel(int userid, int categoryid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("categoryid", categoryid);
		return sqlSession.selectList(Namespace + ".selectLevel", map);
	}

//	@Override
//	public List selectUserScoreExists(int userid, int categoryid) throws Exception {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userid", userid);
//		map.put("categoryid", categoryid);
//		return sqlSession.selectList(Namespace + ".selectUserScoreExists", map);
//	}

	@Override
	public int insertDiagnosisResult(int userid, int categoryid, int selfDiagnosisScore, int selfDiagnosisLevel) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("categoryid", categoryid);
		map.put("selfDiagnosisScore", selfDiagnosisScore);
		map.put("selfDiagnosisLevel", selfDiagnosisLevel);
		return sqlSession.insert(Namespace + ".insertDiagnosisResult", map);
	}
}
