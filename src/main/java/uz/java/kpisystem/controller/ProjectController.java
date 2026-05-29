package uz.java.kpisystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.java.kpisystem.dto.ApiResponse;
import uz.java.kpisystem.dto.project.ProjectFilter;
import uz.java.kpisystem.dto.project.ProjectInfo;
import uz.java.kpisystem.dto.project.ProjectRequest;
import uz.java.kpisystem.service.IProjectService;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final IProjectService service;

    @GetMapping
    public ApiResponse<List<ProjectInfo>> getAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(required = false, defaultValue = "10") Integer limit,
                                                 @RequestParam(required = false, defaultValue = "createdAt") String sortBy) {
        ApiResponse<List<ProjectInfo>> all = service.getAll(new ProjectFilter(page, limit, sortBy));
        return all;
    }

    ;

    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody @Valid ProjectRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectInfo> update(@PathVariable Long id, @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProjectInfo> getOne(@PathVariable Long id) {
        return new ApiResponse<>(service.getOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

}
