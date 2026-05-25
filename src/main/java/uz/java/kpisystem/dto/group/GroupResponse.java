package uz.java.kpisystem.dto.group;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GroupResponse {
    private Long id;
    private String name;
    private Integer taskCount;
}
