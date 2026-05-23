package uz.java.kpisystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.java.kpisystem.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
