package uz.java.kpisystem.mapper;

import org.mapstruct.*;
import uz.java.kpisystem.dto.project.ProjectInfo;
import uz.java.kpisystem.dto.project.ProjectRequest;
import uz.java.kpisystem.entity.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectInfo toResponse(Project project);

    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "group", ignore = true)
    Project toEntity(ProjectRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "group", ignore = true)
    void updateFromRequest(ProjectRequest request, @MappingTarget Project project);
}
