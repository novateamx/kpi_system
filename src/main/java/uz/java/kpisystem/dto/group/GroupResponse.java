package uz.java.kpisystem.dto.group;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupResponse {
    private Long id;
    private String name;
    private Integer taskCount;
}
