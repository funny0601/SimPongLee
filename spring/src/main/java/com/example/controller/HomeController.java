package com.example.controller;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.MemberVO;
import com.example.service.MemberService;
import com.mysql.cj.xdevapi.JsonArray;

/**
 * Handles requests for the application home page.
 */
@RestController
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	private MemberService service;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", produces="application/json; charset=utf-8", method = {RequestMethod.POST,RequestMethod.GET})
	public List<MemberVO> home(Locale locale, Model model) throws Exception{

		logger.info("home");
		
		List<MemberVO> memberList = service.selectMember();
		
		model.addAttribute("memberList", memberList);

		return memberList;
	}
	
}
