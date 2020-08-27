package com.example.dao;

import com.example.dto.UserVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class UserDAOlmpl implements UserDAO{

    @Inject
    private SqlSession sqlSession;

    private static final String Namespace = "com.example.mapper.userMapper";

    @Override
    public String selectUser(String email, String password) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("email", email);
        map.put("password", password);
        String result = sqlSession.selectOne(Namespace+".selectUser", map).toString();
        return  result; //select 할때는 selectList로 List 값으로 반환
    }

    @Override
    public int insertUser(String email, String password, String name, String nickname, String phonenumber) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("email", email);
        map.put("password", password);
        map.put("name", name);
        map.put("nickname", nickname);
        map.put("phonenumber", phonenumber);
        return sqlSession.insert(Namespace+".insertUser", map); //insert 할때는 int 값으로 결과 반환
    }
    @Override
    public Integer checkUser(String nickname) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nickname", nickname);
        return sqlSession.selectOne(Namespace+".checkUser", map); //insert 할때는 string 값으로 결과 반환
    }
    @Override
    public Integer getId(String nickname) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nickname", nickname);
        return sqlSession.selectOne(Namespace+".getId", map); //insert 할때는 string 값으로 결과 반환
    }

    @Override
    public String getNickname(int userid) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userid", userid);
        String result = sqlSession.selectOne(Namespace+".getNickname", map).toString();
        return  result; //select 할때는 selectList로 List 값으로 반환
    }
}
