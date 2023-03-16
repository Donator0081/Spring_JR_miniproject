package com.example.spring_jr_miniproject.repositories;

import com.example.spring_jr_miniproject.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}