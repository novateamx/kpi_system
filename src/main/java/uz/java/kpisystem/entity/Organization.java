package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "organizations")
public class Organization extends Auditable {

    private String name;

    private String logo;

    private String description;

    private String phone;

    private String address;

    private String email;

    private String website;

//    Relations ---> bu 1ta table boshqa bir table lar bilan boglash
//    1) @OneToMany ishlatib 1 ga ko'p boglanish
//    2) @OneToOne 1 ga 1 bir boglanish
//    3) @ManyToOne boglanish - ko'pga bir boglanish
//    4) @MAnyToMany - ko'pga ko'p bog'lanish




//    CRUD operation - Create, Update, Read, Delete

}

