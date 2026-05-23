package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "checklist_items")
public class CheckListItem  extends Auditable {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id", referencedColumnName = "id")
    private CheckList checkList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigner_id")
    private User user;
}
