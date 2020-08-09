package com.example.controller;

import com.example.dto.SelfDiagnosisVO;
import com.example.service.SelfDiagnosisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

public class SelfDiagnosisController {

    private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);

    @Inject
    private SelfDiagnosisService selfDiagnosisService;

    @RequestMapping(value = "/selectQuestion", method = {RequestMethod.POST,RequestMethod.GET})
    public List<SelfDiagnosisVO> selectQuestion(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

        logger.info("selectQuestion");

        String question = httpServletRequest.getParameter("question");
        int questionid = Integer.parseInt(httpServletRequest.getParameter("questionid"));

        List questionList = selfDiagnosisService.selectQuestion(question, questionid);

        return questionList;
    }
}
