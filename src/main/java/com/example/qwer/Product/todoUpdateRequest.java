package com.example.qwer.todo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class todoUpdateRequest {
    private Long userId;
    private String title;
    private String todo;
    private long todoId;
}
