package uz.java.kpisystem.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
public class TaskRequest {
    private String name;
    private String description;
    private String taskStatus;
    private LocalDateTime deadline;
    private String priority;
    private LocalDate startDate;
    private List<String> attachmentUrls;
    private List<Long> assignerIds;
    private String layer;
    private String type;
    private String reviewDueDate;
    private Long parentId;
    private List<Long> tagIds;
}
