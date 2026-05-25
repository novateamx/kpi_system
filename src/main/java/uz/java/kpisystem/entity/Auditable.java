package uz.java.kpisystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(value = {
        "createdBy", "updateBy", "createdAt", "updatedAt"
}, allowGetters = true)
public abstract class Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreationTimestamp
    @JsonIgnore
    LocalDateTime createdAt;

    @LastModifiedDate
    @JsonIgnore
    LocalDateTime updatedAt;

    LocalDateTime deletedAt;

    @CreatedBy
    @JsonIgnore
    Long createdBy;

    @LastModifiedBy
    @JsonIgnore
    Long updatedBy;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    Boolean deleted;

    public void makeAsDeleted() {
        this.deletedAt = LocalDateTime.now();
        this.deleted = true;
    }
}
