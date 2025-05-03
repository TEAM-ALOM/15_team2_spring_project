package com.example.qwer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter // Lombok을 사용하는 경우 추가
@ToString
public class User {
    @Id


    @GeneratedValue(strategy = GenerationType.IDENTITY) // 또는 다른 전략 선택
    private Long id;
    private String username;
    private String email;
}
