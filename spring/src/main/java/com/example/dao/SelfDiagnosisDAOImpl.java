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
		//map.put("question", question);
		map.put("categoryid", categoryid);
		return sqlSession.selectList(Namespace + ".selectQuestion", map);
	}

}
