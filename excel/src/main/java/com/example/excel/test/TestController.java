package com.example.excel.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final TestDao testDao;

    @GetMapping("/test/list")
    public ModelAndView list(ModelAndView modelAndView){
        List<TestDto> list = testDao.selectAll();
        modelAndView.setViewName("test/board");
        modelAndView.addObject("list",list);
        return modelAndView;
    }

}
