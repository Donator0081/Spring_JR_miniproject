package com.example.spring_jr_miniproject.services;

import com.example.spring_jr_miniproject.domain.Task;
import com.example.spring_jr_miniproject.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository repo;


    @Override
    public Task getOneTask(int id) {

        Optional<Task> task = repo.findById(id);
        return task.orElse(null);
    }

    public List<Task> getTasks(int page, int limit){
        PageRequest request = PageRequest.of(page,limit);
        return repo.findAll(request).stream().toList();
    }

    public Task addTask(Task task){
        return repo.saveAndFlush(task);
    }

    public Task updateTask(Task task){
        Optional<Task> optionalTask = repo.findById(task.getId());
        if (optionalTask.isPresent()){
            Task taskForUpdate = optionalTask.get();
            taskForUpdate.setDescription(task.getDescription());
            taskForUpdate.setStatus(task.getStatus());
            return repo.saveAndFlush(task);
        }
       return null;
    }

    public void deleteTask(Task task){
        repo.delete(task);
    }

    @Override
    public long getAllCount() {
        return repo.count();
    }
}
