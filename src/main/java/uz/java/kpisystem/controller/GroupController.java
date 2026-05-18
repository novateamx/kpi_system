package uz.java.kpisystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.java.kpisystem.dto.group.GroupFilter;
import uz.java.kpisystem.dto.group.GroupResponse;
import uz.java.kpisystem.entity.Group;
import uz.java.kpisystem.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService service;

    public GroupController(GroupService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestBody GroupFilter body)  {
        List<GroupResponse> groups = this.service.getAll(body);
        return ResponseEntity.ok(groups);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam String name) {
        return ResponseEntity.ok(service.create(name));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestParam String name) {
        return ResponseEntity.ok(service.update(id, name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
