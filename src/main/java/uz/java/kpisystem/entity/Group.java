package uz.java.kpisystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "groups")
@Builder
public class Group extends Auditable {

    private String name;

    private Integer taskCount;
}
