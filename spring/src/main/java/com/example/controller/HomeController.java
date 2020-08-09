package com.example.controller;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.example.dto.CalendarVO;
import com.example.dto.SelfDiagnosisVO;
import com.example.service.CalendarService;
import com.example.service.SelfDiagnosisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Inject
	private CalendarService calendarService;
	private SelfDiagnosisService selfDiagnosisService;

	@RequestMapping(value = "/selectDiary", method = {RequestMethod.POST,RequestMethod.GET})
	public List<CalendarVO> selectDiary(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

		logger.info("selectDiary");

		String date = httpServletRequest.getParameter("date");
		int userid = Integer.parseInt(httpServletRequest.getParameter("userid"));

		List calendarList = calendarService.selectDiary(date, userid);

		return calendarList;
	}

	@RequestMapping(value = "/putDiary", method = {RequestMethod.POST,RequestMethod.GET})
	public String putDiary(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

		logger.info("putDiary");

		String status = null;

		String date = httpServletRequest.getParameter("date");
		int userid = Integer.parseInt(httpServletRequest.getParameter("userid"));
		String title= httpServletRequest.getParameter("title");
		String body = httpServletRequest.getParameter("body");
		int mood = Integer.parseInt(httpServletRequest.getParameter("mood"));

		List calendarList = calendarService.selectDiary(date, userid);

		if (calendarList.isEmpty()) {
			logger.info("해당 날짜의 다이어리가 비어있습니다 -> insert");
			calendarService.insertDiary(date, userid, title, body, mood);
			status = "finished insert";
		}else {
			logger.info("해당 날짜의 다이어리가 이미 있습니다 -> update");
			calendarService.updateDiary(date, userid, title, body, mood);
			status = "finished update";
		}
		return status;
	}

	@RequestMapping(value = "/selectQuestion", method = {RequestMethod.POST,RequestMethod.GET})
	public List<SelfDiagnosisVO> selectQuestion(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

		logger.info("selectQuestion");

		String question = httpServletRequest.getParameter("question");
		int questionid = Integer.parseInt(httpServletRequest.getParameter("questionid"));

		List questionList = selfDiagnosisService.selectQuestion(question, questionid);

		return questionList;
	}
}
