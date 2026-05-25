package uz.java.kpisystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.java.kpisystem.entity.TaskMember;

public interface TaskMemberRepository extends JpaRepository<TaskMember, Long> {
}
