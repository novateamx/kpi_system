package uz.java.kpisystem.service;

import jakarta.validation.Valid;
import uz.java.kpisystem.dto.project.ProjectFilter;
import uz.java.kpisystem.dto.project.ProjectInfo;
import uz.java.kpisystem.dto.project.ProjectRequest;

import java.util.List;

public interface IProjectService {
    List<ProjectInfo> getAll(ProjectFilter projectFilter);

    Long create(@Valid ProjectRequest request);

    ProjectInfo update(Long id, ProjectRequest request);

    ProjectInfo getOne(Long id);

     Boolean delete(Long id);

}
