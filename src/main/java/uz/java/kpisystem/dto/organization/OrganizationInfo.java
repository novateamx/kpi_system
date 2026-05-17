package uz.java.kpisystem.dto.organization;

import lombok.Data;

@Data
public class OrganizationInfo {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String website;
}
