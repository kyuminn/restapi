package com.example.excel.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String member1(){
        return "member";
    }

    @PostMapping
    public String member2(@ModelAttribute MemberDto memberDto){
        log.info("username={}, email={}, age={}", memberDto.getUsername(), memberDto.getEmail(), memberDto.getAge());
        memberService.addMember(memberDto);
        log.info("==========등록 성공!============");
        return "redirect:/";
    }

    // ResponseBody, RequestBody : 서버와 비동기 통신 (예:ajax) 할 때 사용하는 어노테이션.
    // 비동기통신 할 때 HttpBody에다가 원하는 데이터를 담아서 서버에 보내고, 서버에서 클라이언트로 응답을 보낼때도 HttpBody에 response를 담아서 보낸다
    // 이 때 HttpBody에 담기는 데이터 형식은 대부분 json 형태임.
    // @RequestBody, @ResponseBody 어노테이션이 json 형태의 데이터를 java 객체로 파싱해준다.
    // cf : @RestController : @ResponseBody+@Controller
    // https://cheershennah.tistory.com/179

    @PostMapping("/json")
    @ResponseBody
    public MemberDto member3(@RequestBody MemberDto memberDto){ //@RequestBody는 Http request body(post로 보낸 json data)를 java 객체로 전달받을 수 있다.
        log.info("==json format==>username={}, email={}, age={}", memberDto.getUsername(), memberDto.getEmail(), memberDto.getAge());
        return memberDto; //ajax의 success method로 전달.
    }

    @GetMapping("/list")
    public String board(Model model){
        List<MemberDto> memberList = memberService.getAllMember();
        model.addAttribute("list",memberList);
        return "board";
    }

    @GetMapping("/detail/{email}")
    public String detail(@PathVariable("email") String email , Model model){
        log.info("email={}",email);
        MemberDto memberDto = memberService.findByEmail(email);
        model.addAttribute("member",memberDto);
        return "detail";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam("email") String email,Model model){
        log.info("edit email={}", email);
        MemberDto memberDto = memberService.findByEmail(email);
        model.addAttribute("member", memberDto);
        return "edit";
    }
}
