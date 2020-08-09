package com.example.dao;

import com.example.dto.CalendarVO;

import java.util.List;

public interface CalendarDAO {
    public List selectDiary(String date, int userid) throws Exception;
    public int insertDiary(String date, int userid, String title, String body, int mood) throws Exception;
    public int updateDiary(String date, int userid, String title, String body, int mood) throws Exception;
}