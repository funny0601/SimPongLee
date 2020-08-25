package com.example.controller;

import com.example.dto.CommentVO;
import com.example.service.CommentService;
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
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Inject
    private CommentService commentService;

    @RequestMapping(value = "/selectComment", method = {RequestMethod.POST,RequestMethod.GET})
    public List<CommentVO> selectComment(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

        logger.info("selectComment");

        int categoryid = Integer.parseInt(httpServletRequest.getParameter("categoryid"));
        String selfDiagnosisLevel = httpServletRequest.getParameter("selfDiagnosisLevel");

        List levelList = commentService.selectComment(categoryid, selfDiagnosisLevel);

        return levelList;
    }
}
