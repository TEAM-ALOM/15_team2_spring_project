package com.example.qwer.todo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }
    @PostMapping("/{userId}")
    public todoResponse createTodo(@PathVariable Long userId, @RequestBody Todo todo){
        Todo createdTodo=todoService.createTodo(todo, userId);
        System.out.println("Post 메소드 실행"+createdTodo+"\nTodoId: "+createdTodo.getId());
        todoResponse TodoResponse = new todoResponse();

        return TodoResponse;
    }

    @PutMapping("/{userId}")
    public Todo updateTodo(@PathVariable Long userId, @RequestBody Todo todo){
        Todo currentTodo = todoService.updateTodo(todo, userId);
        System.out.println("Post 메소드 실행"+currentTodo);
        return currentTodo;
    }

    @GetMapping("/{userId}")
    public List<Todo> getTodo(@PathVariable Long userId){
        System.out.println("Post 메소드 실행");
        return todoService.getTodo(userId);
    }

    @DeleteMapping()
    public String deleteTodo(@RequestBody todoUpdateRequest dto){
        System.out.println("Post 메소드 실행");
        todoService.deleteTodo(dto.getTodoId());
        return "Todo 삭제 메소드";
    }

    //return ResponseEntity.status(HttpStatus.OK).body(todo1);


}
