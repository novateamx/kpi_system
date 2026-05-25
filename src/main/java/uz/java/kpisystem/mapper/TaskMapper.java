package uz.java.kpisystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.java.kpisystem.dto.task.TaskRequest;
import uz.java.kpisystem.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "parentId", ignore = true)
    Task toEntity(TaskRequest request);
}
