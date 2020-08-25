package com.example.controller;

import com.example.dto.UserVO;
import com.example.service.UserService;
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
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserService userService;

    @RequestMapping(value = "/selectUser", method = {RequestMethod.POST,RequestMethod.GET})
    public String selectUser(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

        logger.info("selectUser");
        String email = httpServletRequest.getParameter("email");
        String password = httpServletRequest.getParameter("password");

        String user = userService.selectUser(email,password);

        return user;
    }

    @RequestMapping(value = "/insertUser", method = {RequestMethod.POST,RequestMethod.GET})
    public String insertUser(Locale locale, HttpServletRequest httpServletRequest) throws Exception{

        logger.info("insertUser");
        String name = httpServletRequest.getParameter("name");
        String email = httpServletRequest.getParameter("email");
        String password = httpServletRequest.getParameter("password");
        String nickname = httpServletRequest.getParameter("nickname");
        String phonenumber = httpServletRequest.getParameter("phonenumber");


        userService.insertUser(email, password, name, nickname, phonenumber);

        return "UserSuccessfullyInserted";
    }

    @RequestMapping(value = "/checkUser", method = {RequestMethod.POST,RequestMethod.GET})
    public int checkUser(Locale locale, HttpServletRequest httpServletRequest) throws Exception{


        String nickname = httpServletRequest.getParameter("nickname");

        Integer check = userService.checkUser(nickname);
        int count=  check.intValue();
        logger.info("checkUser"+count);

        return count ;
    }

}
