package com.example.dao;

import com.example.dto.CalendarVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CalendarDAOImpl implements CalendarDAO {

	@Inject
	private SqlSession sqlSession;
	
	private static final String Namespace = "com.example.mapper.calendarMapper";
	
	@Override
	public List selectDiary(String date, int userid) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", date);
		map.put("userid", userid);
		return sqlSession.selectList(Namespace+".selectDiary", map);
	}

	@Override
	public int insertDiary(String date, int userid, String title, String body, int mood) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", date);
		map.put("userid", userid);
		map.put("title", title);
		map.put("body", body);
		map.put("mood", mood);
		return sqlSession.insert(Namespace+".insertDiary", map);
	}

	@Override
	public int updateDiary(String date, int userid, String title, String body, int mood) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", date);
		map.put("userid", userid);
		map.put("title", title);
		map.put("body", body);
		map.put("mood", mood);
		return sqlSession.update(Namespace+".updateDiary",map);
	}

}
