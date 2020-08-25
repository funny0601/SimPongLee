package com.example.service;

import com.example.dto.UserVO;
import java.util.List;

public interface UserService {
    public String selectUser(String email, String password) throws Exception;

    public int insertUser(String email, String password, String name, String nickname, String phonenumber) throws Exception;

    public Integer checkUser(String nickname) throws Exception;

}
