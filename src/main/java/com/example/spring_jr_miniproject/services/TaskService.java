package com.example.spring_jr_miniproject.services;

import com.example.spring_jr_miniproject.domain.Task;

import java.util.List;

public interface TaskService {

    Task getOneTask(int id);
    List<Task> getTasks(int page, int limit);

    Task addTask(Task task);

    Task updateTask(Task task);

    void deleteTask(Task task);

    long getAllCount();
}
