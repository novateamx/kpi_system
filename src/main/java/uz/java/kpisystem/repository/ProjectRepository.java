package uz.java.kpisystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.java.kpisystem.entity.Project;

public interface ProjectRepository extends JpaRepository<Project,Long> {}
