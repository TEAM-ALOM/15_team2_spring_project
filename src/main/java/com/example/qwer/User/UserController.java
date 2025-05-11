package com.example.qwer.user;

import com.example.qwer.user.dto.UserRequestDto;
import com.example.qwer.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .build();

        User saved = userService.createUser(user);
        return ResponseEntity.ok(new UserResponseDto(saved));
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> getUser(@RequestParam String username) {
        return userService.getUserByUsername(username)
                .map(user -> ResponseEntity.ok(new UserResponseDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }
}
