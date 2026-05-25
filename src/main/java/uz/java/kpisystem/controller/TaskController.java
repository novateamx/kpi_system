package uz.java.kpisystem.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.java.kpisystem.dto.ApiResponse;
import uz.java.kpisystem.dto.task.TaskRequest;
import uz.java.kpisystem.service.ITaskService;
import uz.java.kpisystem.util.ApiVersion;

@RestController
@RequestMapping(ApiVersion.API_VERSION + "/tasks")
public class TaskController {

    private final ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ApiResponse<Long> create(@RequestBody @Valid TaskRequest request) {
        return new ApiResponse<>(taskService.create(request));
    }
}
