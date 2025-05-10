package com.example.qwer.todo;


import com.example.qwer.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String todo;

    //1:N N쪽이 연관관계의 주인
    //many가 todo고 one이 user이다.

    @ManyToOne
    private User user;

    public void setId(Long id){
        this.id = id;
    }
    public Long getId(){
        return id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTodo(String todo){
        this.todo = todo;
    }

    public String getTodo(){
        return todo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
