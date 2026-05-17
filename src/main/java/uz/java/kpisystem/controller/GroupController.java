package uz.java.kpisystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.java.kpisystem.dto.group.GroupFilter;
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

    @PostMapping("/all")
    public ResponseEntity<List<Group>> getAll(@RequestBody GroupFilter body)  {
        List<Group> groups = this.service.getAll(body);
        return ResponseEntity.ok(groups);
    }

    @PostMapping("/create")
    public ResponseEntity<Group> create(@RequestBody Group body) {
        Group group = this.service.create(body);
        return  ResponseEntity.ok(group);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Group> update(@PathVariable Long id,@RequestBody Group body) {
        Group group = this.service.update(id,body);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getOne(@PathVariable Long id) {
        Group group = this.service.getOne(id);
        return ResponseEntity.ok(group);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Group> delete(@PathVariable Long id) {
        Group group = this.service.delete(id);
        return ResponseEntity.ok(group);
    }
}
