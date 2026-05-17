package uz.java.kpisystem.service;

import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import uz.java.kpisystem.dto.project.ProjectFilter;
import uz.java.kpisystem.dto.project.ProjectInfo;
import uz.java.kpisystem.dto.project.ProjectRequest;

import java.util.List;

public interface IProjectService {
    List<ProjectInfo> getAll(ProjectFilter projectFilter);

    Long create(@Valid ProjectRequest request);

    @Nullable ProjectInfo update(Long id, ProjectRequest request);

    ProjectInfo getOne(Long id);

     Boolean delete(Long id);

}
