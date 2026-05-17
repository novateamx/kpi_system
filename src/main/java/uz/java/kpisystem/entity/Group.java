package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @Data @NoArgsConstructor
@Entity @Table(name = "groups")
public class Group extends Auditable {

    private String name;

    private Integer taskCount;
}
