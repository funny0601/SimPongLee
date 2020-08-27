package com.example.dao;

import com.example.dto.UserVO;

import java.util.List;

public interface UserDAO {
    public String selectUser(String id, String password) throws Exception;
    public int insertUser(String email, String password, String name, String nickname, String phonenumber) throws Exception;
    public Integer checkUser(String nickname) throws Exception;
    public Integer getId(String nickname) throws Exception;
    public String getNickname(int userid) throws Exception;

}
