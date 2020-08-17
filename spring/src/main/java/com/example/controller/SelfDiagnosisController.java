package com.example.controller;

import com.example.dto.SelfDiagnosisResultVO;
import com.example.dto.SelfDiagnosisVO;
import com.example.service.SelfDiagnosisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@RestController
public class SelfDiagnosisController {

    private static final Logger logger = LoggerFactory.getLogger(SelfDiagnosisController.class);

    @Inject
    private SelfDiagnosisService selfDiagnosisService;

    @RequestMapping(value = "/selectQuestion", method = {RequestMethod.POST,RequestMethod.GET})
    public List<SelfDiagnosisVO> selectQuestion(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

        logger.info("selectQuestion");

        //String category = httpServletRequest.getParameter("category");
        int categoryid = Integer.parseInt(httpServletRequest.getParameter("categoryid"));

        List questionList = selfDiagnosisService.selectQuestion(categoryid);

        return questionList;
    }

    @RequestMapping(value = "/selectLevel", method = {RequestMethod.POST,RequestMethod.GET})
    public List<SelfDiagnosisResultVO> selectLevel(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

        logger.info("selectLevel");

        int userid = Integer.parseInt(httpServletRequest.getParameter("userid"));
        int categoryid = Integer.parseInt(httpServletRequest.getParameter("categoryid"));

        List levelList = selfDiagnosisService.selectLevel(userid, categoryid);

        return levelList;
    }

//    @RequestMapping(value = "/selectUserScoreExists", method = {RequestMethod.POST,RequestMethod.GET})
//    public List<SelfDiagnosisResultVO> selectUserScoreExists(Locale locale, HttpServletRequest httpServletRequest) throws Exception{
//
//        logger.info("selectUserScoreExists");
//
//        int userid = Integer.parseInt(httpServletRequest.getParameter("userid"));
//        int categoryid = Integer.parseInt(httpServletRequest.getParameter("categoryid"));
//
//        List levelList = selfDiagnosisService.selectUserScoreExists(userid, categoryid);
//
//        return levelList;
//    }

    @RequestMapping(value = "/insertDiagnosisResult", method = {RequestMethod.POST,RequestMethod.GET})
    public String insertDiagnosisResult(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

        logger.info("insertDiagnosisResult");

        int userid = Integer.parseInt(httpServletRequest.getParameter("userid"));
        int categoryid = Integer.parseInt(httpServletRequest.getParameter("categoryid"));
        int selfDiagnosisScore = Integer.parseInt(httpServletRequest.getParameter("selfDiagnosisScore"));
        int selfDiagnosisLevel = Integer.parseInt(httpServletRequest.getParameter("selfDiagnosisLevel"));

        selfDiagnosisService.insertDiagnosisResult(userid, categoryid, selfDiagnosisScore, selfDiagnosisLevel);

        return "ResultSuccessfullyInserted";
    }
}
