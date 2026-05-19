package uz.java.kpisystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.java.kpisystem.entity.SessionUser;

public interface SessionUserRepository extends JpaRepository<SessionUser, Long> {
    SessionUser findByUserId(Long id);
}