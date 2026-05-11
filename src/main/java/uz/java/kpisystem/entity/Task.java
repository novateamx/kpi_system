package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.java.kpisystem.entity.enums.TaskLayer;
import uz.java.kpisystem.entity.enums.TaskPriority;
import uz.java.kpisystem.entity.enums.TaskStatus;
import uz.java.kpisystem.entity.enums.TaskType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "time_estimate")
    private Long timeEstimate;

    private Long trackTimeInMinutes;

    @ElementCollection
    private List<String> attachmentUrls = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private TaskLayer layer;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Column(name = "review_due_date")
    private LocalDateTime reviewDueDate;

    private Long parentId;
}
