package com.example.qwer;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user") // 공통 경로 수정
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    } // 의존성 주입

    // 유저 생성: JSON 요청 + 유저 정보 받고 User 객체 만들어 저장
    @PostMapping
    public String createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        System.out.println("생성된 유저 정보: "+createdUser);
        return "유저 생성 성공";
    }

    // 유저 수정: JSON 요청 + 유저 정보 받고 User 객체 정보 수정
    /*
    @PutMapping("/{userId}")
    public String updateUser(@PathVariable("userId") Long id, @RequestBody User user) {
        user.setId(id); // URL 변수에서 ID 설정
        User updatedUser=userService.updateUser(user);
        System.out.println("업데이트된 유저 정보: "+updatedUser);
        return "유저 업데이트 완료";
    }
    */

    // 유저 삭제: URL 변수 요청 + 유저 ID 받고 유저 삭제
    @DeleteMapping()
    public String deleteUser(@RequestParam Long userId) { // ?userId=(숫자)
        userService.deleteUser(userId);
        return "유저정보 삭제 완료";
    }

    // 유저 조회: URL 변수 요청 + 유저 ID 받고 유저 반환
    @GetMapping("/{userId}")
    public String getUser(@PathVariable Long userId) {
        User currentUser=userService.getUser(userId);
        System.out.println("조회된 유저 정보: "+currentUser);
        return "유저 정보 반환 완료";
    }
}