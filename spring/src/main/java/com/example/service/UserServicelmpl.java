package com.example.service;

import com.example.dao.CalendarDAO;
import com.example.dao.UserDAO;
import com.example.dto.UserVO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class UserServicelmpl implements UserService{

    @Inject
    private UserDAO dao;

    @Override
    public String selectUser(String email, String password) throws Exception {
        return dao.selectUser(email, password);
    }

    @Override
    public int insertUser(String email, String password, String name, String nickname, String phonenumber) throws Exception {
        return dao.insertUser(email, password, name, nickname, phonenumber);
    }
    @Override
    public Integer checkUser(String nickname) throws Exception {
        return dao.checkUser(nickname);
    }
}
