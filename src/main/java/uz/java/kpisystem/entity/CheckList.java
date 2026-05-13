package uz.java.kpisystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "checklist")
public class CheckList extends Auditable {

    private String name;

    @OneToMany(mappedBy = "checkList")
    private Set<CheckListItem> items = new HashSet<>();
}
