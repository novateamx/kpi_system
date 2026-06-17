package uz.java.kpisystem.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import uz.java.kpisystem.entity.User;
import uz.java.kpisystem.filter.UserFilter;

import java.util.ArrayList;
import java.util.List;

public record UserSpecification(UserFilter filter)
        implements Specification<User> {

    @Override
    public Predicate toPredicate(Root<User> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList();

        if (filter.getFirstName() != null) {
            predicates.add(
                    cb.like(
                            cb.lower(root.get("firstName")),
                            "%" + filter.getFirstName().toLowerCase() + "%"
                    )
            );
        }

        if (filter.getLastName() != null) {
            predicates.add(
                    cb.like(
                            cb.lower(root.get("lastName")),
                            "%" + filter.getLastName().toLowerCase() + "%"
                    )
            );
        }

        if (filter.getUsername() != null) {
            predicates.add(
                    cb.like(
                            cb.lower(root.get("username")),
                            "%" + filter.getUsername().toLowerCase() + "%"
                    )
            );
        }

        if (filter.getRoleId() != null) {
            predicates.add(
                    cb.equal(
                            root.get("role").get("id"),
                            filter.getRoleId()
                    )
            );
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}