package uz.java.kpisystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.java.kpisystem.entity.Project;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    @Query("select t from Project t join fetch t.organization")
    List<Project> findAllCustom();
}
