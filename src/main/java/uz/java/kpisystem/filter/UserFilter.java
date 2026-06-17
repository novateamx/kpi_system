package uz.java.kpisystem.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFilter {
    private Integer page;
    private Integer limit;
    private String sortBy;

    private String firstName;
    private String lastName;
    private String username;
    private Long roleId;
}