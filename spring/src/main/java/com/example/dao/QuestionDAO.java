package com.example.dao;

import java.util.List;

public interface QuestionDAO {
    public List selectQuestion(int categoryid) throws Exception;
}