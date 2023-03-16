package com.example.spring_jr_miniproject.controllers;

import com.example.spring_jr_miniproject.domain.Status;
import com.example.spring_jr_miniproject.domain.Task;
import com.example.spring_jr_miniproject.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    @GetMapping("/")
    public String getAllTasks(Model model,
                              @ModelAttribute("task") Task task,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<Task> tasks = service.getTasks(page - 1, limit);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);
        long totalPages = (long) Math.ceil(1.0 * service.getAllCount() / limit);
        if (totalPages > 1) {
            List<Integer> page_numbers = IntStream.rangeClosed(1, (int) totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", page_numbers);
        }
        return "index";
    }


    @GetMapping("/{id}")
    public String getTask(Model model, @PathVariable Integer id) {
        model.addAttribute("task", service.getOneTask(id));
        return "task_page";
    }

    @PatchMapping("/{id}")
    public String updateTask(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "task_page";
        }
        service.updateTask(task);
        return "redirect:/tasks/";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Integer id) {
        Task task = service.getOneTask(id);
        service.deleteTask(task);
        return "redirect:/tasks/";
    }

    @PostMapping("/")
    public String createTask(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/tasks/";
        }
        service.addTask(task);
        return "redirect:/tasks/";
    }


    @ModelAttribute
    public void addAttributes(Model model) {
        List<String> statuses = Arrays.stream(Status.values()).map(Enum::toString).toList();
        model.addAttribute("statuses", statuses);
    }
}
