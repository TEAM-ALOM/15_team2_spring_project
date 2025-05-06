package com.example.qwer.todo;


import com.example.qwer.User;
import com.example.qwer.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository){
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }
    public Todo createTodo(Todo todo, Long id){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        todo.setUser(existingUser);
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Todo todo, long id) {
        //User existingUser = userRepository.findById(id)
        //        .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        // new List()와 같이 인스턴스화 할 수 없음.

        User existingUser = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User를 찾을 수 없습니다."));
        Optional<Todo> curtodoOpt = todoRepository.findById(todo.getId());
        Todo curtodo=curtodoOpt
                .orElseThrow(()-> new RuntimeException("Todo를 찾을 수 없습니다."));
        //이 user이 가지고 있는 todo가 맞는지 확인하는 과정 필요
        if(!curtodo.getUser().getId().equals(id)) {
            throw new RuntimeException("userId와 todoId가 일치하지 않음.");
        }

        curtodo.setTitle(todo.getTitle());
        curtodo.setTodo(todo.getTodo());
        return todoRepository.save(todo);
    }

    public List<Todo> getTodo(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        return todoRepository.findAllByUserId(existingUser.getId());
    }

    public void deleteTodo(Long id){
        Todo curtodo = todoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Todo를 찾을 수 없습니다."));
        todoRepository.deleteById(id);
    }

}
