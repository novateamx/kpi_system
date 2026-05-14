package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task_action")
public class TaskAction  extends Auditable{

    private Long projectId;

    private Long projectViewId;

    private Long taskId;

    private String name;

    private String type;

}
