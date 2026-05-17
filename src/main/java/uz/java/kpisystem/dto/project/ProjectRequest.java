package uz.java.kpisystem.dto.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.java.kpisystem.entity.enums.ProjectStatus;

import java.time.LocalDate;

@Data
public class ProjectRequest {
    @NotNull(message = "name must not be null")
    @NotBlank(message = "name must not be blank")
    private String name;
    private String description;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate startDate;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate endDate;
    private ProjectStatus status;
    private Long organizationId;
    private Long groupId;
}
