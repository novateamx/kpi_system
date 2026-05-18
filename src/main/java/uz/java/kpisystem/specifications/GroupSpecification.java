package uz.java.kpisystem.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import uz.java.kpisystem.dto.group.GroupFilter;
import uz.java.kpisystem.entity.Group;

import java.util.ArrayList;
import java.util.List;

public record GroupSpecification(GroupFilter filter) implements Specification<Group> {
    @Override
    public Predicate toPredicate(Root<Group> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getName() != null)
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                    "%" + filter.getName().toLowerCase() + "%"));
        if (filter.getTaskCount() != null)
            predicates.add(criteriaBuilder.equal(root.get("taskCount"), filter.getTaskCount()));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
