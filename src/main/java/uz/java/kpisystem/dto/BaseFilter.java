package uz.java.kpisystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseFilter {
    private Integer page;
    private Integer limit;
    private String sortBy;
}
