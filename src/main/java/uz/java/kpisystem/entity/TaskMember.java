package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task_members")
@Getter
@Setter
public class TaskMember extends Auditable {
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private User user;
}
