package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "task_action")
public class TaskAction extends Auditable{

    private Long projectId;

    private Long projectViewId;

    private Long taskId;

    private String name;

    private String type;
}
