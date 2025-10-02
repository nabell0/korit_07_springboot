package com.example.todolist;

import com.example.todolist.domain.AppUserRepository;
import com.example.todolist.domain.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodolistApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(TodolistApplication.class);

    private final AppUserRepository appUserRepository;
    private final TodoRepository todoRepository;

    public TodolistApplication(AppUserRepository appUserRepository, TodoRepository todoRepository) {
        this.appUserRepository = appUserRepository;
        this.todoRepository = todoRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

    }
}
