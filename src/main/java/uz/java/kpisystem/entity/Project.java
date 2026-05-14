package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.java.kpisystem.entity.enums.ProjectStatus;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "projects")
public class Project  extends Auditable  {


    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
