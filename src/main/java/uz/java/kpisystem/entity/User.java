package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends Auditable {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true, length = 300)
    private String email;

    private String phone;

    private LocalDateTime deletedAt;

    private String status;

    private String photoUrl;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @ManyToMany
    @JoinTable(name = "user_organizations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id"))
    private Set<Organization> organization = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
