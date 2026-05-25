package uz.java.kpisystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.java.kpisystem.entity.TaskTag;

public interface TaskTagRepository extends JpaRepository<TaskTag, Long> {
}
