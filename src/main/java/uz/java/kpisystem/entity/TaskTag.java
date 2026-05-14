package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task_tags")
public class TaskTag extends Auditable {
    private String name;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
