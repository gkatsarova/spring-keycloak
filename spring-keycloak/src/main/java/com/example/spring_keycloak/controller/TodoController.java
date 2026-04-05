package com.example.spring_keycloak.controller;

import com.example.spring_keycloak.model.Todo;
import com.example.spring_keycloak.repository.TodoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the Spring Boot + Keycloak TODO App! Try <a href='/public'>/public</a> or <a href='/me'>/me</a>.";
    }

    @GetMapping("/public")
    public String publicPage() {
        return "This is a public page accessible without logging in.";
    }

    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal OidcUser user) {
        return user.getClaims();
    }

    @GetMapping("/todos")
    public List<Todo> myTodos(@AuthenticationPrincipal OidcUser user) {
        return todoRepository.findByOwner(user.getPreferredUsername());
    }

    @PostMapping("/todos")
    public Todo addTodo(@RequestBody Todo todo, @AuthenticationPrincipal OidcUser user) {
        todo.setOwner(user.getPreferredUsername());
        return todoRepository.save(todo);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Todo> allTodos() {
        return todoRepository.findAll();
    }
}
