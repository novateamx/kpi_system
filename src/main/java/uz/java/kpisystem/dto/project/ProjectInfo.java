package uz.java.kpisystem.dto.project;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uz.java.kpisystem.dto.organization.OrganizationInfo;
import uz.java.kpisystem.entity.Group;
import uz.java.kpisystem.entity.enums.ProjectStatus;

import java.time.LocalDate;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ProjectInfo {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;
    private OrganizationInfo organization;
    private Group group;
    private boolean deleted;
}

