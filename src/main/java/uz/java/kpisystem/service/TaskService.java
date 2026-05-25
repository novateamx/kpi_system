package uz.java.kpisystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.kpisystem.dto.task.TaskRequest;
import uz.java.kpisystem.entity.Task;
import uz.java.kpisystem.entity.TaskMember;
import uz.java.kpisystem.entity.TaskTag;
import uz.java.kpisystem.entity.User;
import uz.java.kpisystem.exception.CustomNotFoundException;
import uz.java.kpisystem.mapper.TaskMapper;
import uz.java.kpisystem.repository.TaskMemberRepository;
import uz.java.kpisystem.repository.TaskRepository;
import uz.java.kpisystem.repository.TaskTagRepository;
import uz.java.kpisystem.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {

    private final TaskMapper mapper;
    private final TaskRepository repository;
    private final UserRepository userRepository;
    private final TaskMemberRepository taskMemberRepository;
    private final TaskTagRepository taskTagRepository;

    @Override
    @Transactional
    public Long create(TaskRequest request) {
        Task task = mapper.toEntity(request);
        if (!request.getAssignerIds().isEmpty()) {
            request.getAssignerIds().stream().forEach(assignerId -> {
                User user = userRepository.findById(assignerId).orElseThrow(
                        () -> new CustomNotFoundException("member.not.not.found")
                );
                TaskMember member = new TaskMember();
                member.setUser(user);
                member.setTask(task);
                taskMemberRepository.save(member);
            });
        }
        if (!request.getTagIds().isEmpty()) {
            request.getTagIds().stream().forEach(tagId -> {
                TaskTag taskTag = taskTagRepository.findById(tagId).orElseThrow(
                        () -> new CustomNotFoundException("task.tag.not.not.found")
                );
                taskTag.setTask(task);
                taskTagRepository.save(taskTag);
            });
        }
        if (request.getParentId() != null) {
            task.setParentId(request.getParentId());
        }
        repository.save(task);
        return task.getId();
    }
}
