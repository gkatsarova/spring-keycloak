package com.example.spring_keycloak.repository;

import com.example.spring_keycloak.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String> {
    List<Todo> findByOwner(String owner);
}
