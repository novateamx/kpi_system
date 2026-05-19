package uz.java.kpisystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {

    private Long id;
    private String firstName;
    private String lastName;
    private Long organizationId;
}
