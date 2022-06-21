package com.example.excel.test;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestDao {

    public List<TestDto> selectAll();
}
