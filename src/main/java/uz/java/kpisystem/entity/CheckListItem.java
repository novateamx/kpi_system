package uz.java.kpisystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "checklist_items")
public class CheckListItem extends Auditable {

    private String name;

    @ManyToOne
    @JoinColumn(name = "checklist_id", referencedColumnName = "id")
    private CheckList checkList;

    @ManyToOne
    @JoinColumn(name = "assigner_id")
    private User user;
}
