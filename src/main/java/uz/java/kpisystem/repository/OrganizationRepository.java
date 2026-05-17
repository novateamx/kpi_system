package uz.java.kpisystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.java.kpisystem.entity.Organization;
// bean anotatsiya bu yerda qoyilmaydi chuni Jpa ni ichida ozi bor
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
