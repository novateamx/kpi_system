package uz.java.kpisystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.java.kpisystem.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByKeycloakId(UUID keycloakId);
}
