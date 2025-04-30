package com.example.qwer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") // 공통 경로 설정
public class TestController {

    @GetMapping("/person/{message}")
    public String getMethod(@PathVariable String message) {
        return "GetMapping 실행, Get 응답 변환 성공."+ message;
    }

    @PostMapping()
    public String postMethod(){
        return "PostMapping 실행, Post 응답 변환 성공";
    }

    @PutMapping("/team")
    public String putMethod(){
        return "PutMapping 실행, Put 응답 변환 성공";
    }

    @DeleteMapping("/person")
    public String deleteMethod(){
        return "DeleteMapping 실행, Delete 응답 변환 성공";
    }
}
