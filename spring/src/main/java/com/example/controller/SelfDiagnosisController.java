package com.example.controller;

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

    @RequestMapping(value = "/selectLevel", method = {RequestMethod.POST,RequestMethod.GET})
    public List<SelfDiagnosisVO> selectLevel(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

        logger.info("selectLevel");

        int userid = Integer.parseInt(httpServletRequest.getParameter("userid"));

        List levelList = selfDiagnosisService.selectLevel(userid);

        return levelList;
    }

    @RequestMapping(value = "/selectStartLevel", method = {RequestMethod.POST,RequestMethod.GET})
    public List<SelfDiagnosisVO> selectStartLevel(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

        logger.info("selectStartLevel");

        int userid = Integer.parseInt(httpServletRequest.getParameter("userid"));
        int categoryid = Integer.parseInt(httpServletRequest.getParameter("categoryid"));

        List levelList = selfDiagnosisService.selectStartLevel(userid, categoryid);

        return levelList;
    }

    @RequestMapping(value = "/selectResult", method = {RequestMethod.POST,RequestMethod.GET})
    public List<SelfDiagnosisVO> selectResult(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

        logger.info("selectResult");

        int userid = Integer.parseInt(httpServletRequest.getParameter("userid"));
        int categoryid = Integer.parseInt(httpServletRequest.getParameter("categoryid"));

        List resultList = selfDiagnosisService.selectResult(userid, categoryid);

        return resultList;
    }

    @RequestMapping(value = "/putResult", method = {RequestMethod.POST,RequestMethod.GET})
    public String putResult(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

        logger.info("putResult");

        String status = null;

        int userid = Integer.parseInt(httpServletRequest.getParameter("userid"));
        int categoryid = Integer.parseInt(httpServletRequest.getParameter("categoryid"));
        int selfDiagnosisScore = Integer.parseInt(httpServletRequest.getParameter("selfDiagnosisScore"));
        String selfDiagnosisLevel = httpServletRequest.getParameter("selfDiagnosisLevel");

        List resultList = selfDiagnosisService.selectResult(userid, categoryid);

        if (resultList.isEmpty()) {
            logger.info("해당 유저의 카테고리 점수가 비어있습니다 -> insert");
            selfDiagnosisService.insertResult(userid, categoryid, selfDiagnosisScore, selfDiagnosisLevel);
            status = "finished insert";
        }else {
            logger.info("해당 유저의 카테고리 점수가 이미 있습니다 -> update");
            selfDiagnosisService.updateResult(userid, categoryid, selfDiagnosisScore, selfDiagnosisLevel);
            status = "finished update";
        }
        return status;
    }
}
