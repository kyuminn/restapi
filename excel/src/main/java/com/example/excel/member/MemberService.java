package com.example.excel.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberDao;

    public void addMember(MemberDto memberDto){
        memberDao.insertMember(memberDto);
    }

    public List<MemberDto> getAllMember(){
        return memberDao.selectAll();
    }

    public List<MemberDto> getMemberList(MemberDto memberDto){
        return memberDao.selectMemberList(memberDto);
    }

    public MemberDto findByEmail(String email){
        return memberDao.selectOne(email);
    }

}
