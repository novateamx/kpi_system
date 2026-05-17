package uz.java.kpisystem.dto.organization;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OrganizationRequest {
    @NotNull(message = "name must not be null")
    @NotBlank(message = "name must not be blank")
    private String name;
    private String address;
//    @Pattern(regexp = "")
    private String phone;
    @Email(message = "email.must.be.valid")
    private String email;
    private String website;
}
