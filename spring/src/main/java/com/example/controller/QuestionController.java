package com.example.controller;

import com.example.dto.QuestionVO;
import com.example.service.QuestionService;
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
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Inject
    private QuestionService questionService;

    @RequestMapping(value = "/selectQuestion", method = {RequestMethod.POST,RequestMethod.GET})
    public List<QuestionVO> selectQuestion(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

        logger.info("selectQuestion");

        int categoryid = Integer.parseInt(httpServletRequest.getParameter("categoryid"));

        List questionList = questionService.selectQuestion(categoryid);

        return questionList;
    }
}
