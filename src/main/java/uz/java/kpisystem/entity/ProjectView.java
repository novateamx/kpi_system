package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "project_views")
public class ProjectView extends Auditable {

    private String name;

    private String type;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
