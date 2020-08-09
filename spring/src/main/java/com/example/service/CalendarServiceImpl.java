package com.example.service;

import com.example.dao.CalendarDAO;
import com.example.dto.CalendarVO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {

	@Inject
	private CalendarDAO dao;

	@Override
	public List selectDiary(String date, int userid) throws Exception {
		return dao.selectDiary(date, userid);
	}

	@Override
	public int insertDiary(String date, int userid, String title, String body, int mood) throws Exception {
		return dao.insertDiary(date, userid, title, body, mood);
	}

	@Override
	public int updateDiary(String date, int userid, String title, String body, int mood) throws Exception {
		return dao.updateDiary(date, userid, title, body, mood);
	}

}