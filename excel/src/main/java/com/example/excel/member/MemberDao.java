package com.example.excel.member;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {

    public void insertMember(MemberDto memberDto);
    public List<MemberDto> selectAll();
    public MemberDto selectOne(String email);
    public List<MemberDto> selectMemberList(MemberDto memberDto);
}
