package org.globaroman.petshopba.controller;

import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.dto.CreateRequestDto;
import org.globaroman.petshopba.dto.TestDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestController {

    //@GetMapping
    public String getPageIndex() {
        return "index.html";
    }

    @PostMapping
    @ResponseBody
    public TestDto createDto(@RequestBody CreateRequestDto requestDto) {
        System.out.println(requestDto);
        TestDto test = new TestDto();
        test.setName("TeamProject@38");
        return test;
    }

    @GetMapping
    @ResponseBody
    public TestDto getDto() {
        TestDto test = new TestDto();
        test.setName("TeamProject@38");
        return test;
    }

}
