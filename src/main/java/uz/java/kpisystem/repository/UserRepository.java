package uz.java.kpisystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.java.kpisystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
