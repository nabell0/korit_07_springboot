package com.example.todolist.service;

import com.example.todolist.domain.AppUser;
import com.example.todolist.domain.Todo;
import com.example.todolist.domain.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getTodos() {

        return todoRepository.findAll();
    }
    public Todo addTodo(Todo todo) {

        return todoRepository.save(todo);
    }
    public Optional<Todo> getTodoById(Long id){
        return todoRepository.findById(id);
    }
    public boolean deleteTodo(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Transactional
    public Optional<Todo> updateTodoStatus(Long id, Todo todoDetails){
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setAppUser(todoDetails.getAppUser());
                    todo.setCompleted(!todoDetails.isCompleted());
                    return todo;
                });
    }
    @Transactional
    public Optional<Todo> clearCompletedTodos(Long id){
        return todoRepository.findById(id)
                .filter(Todo::isCompleted)
                .map(todo -> {
                    todoRepository.delete(todo);
                return todo;
                });
    }
}
