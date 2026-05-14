package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor @Getter @Setter
@NoArgsConstructor
@Entity @Table(name = "groups")
public class Group  extends Auditable {

    private String name;

    private Integer taskCount;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
