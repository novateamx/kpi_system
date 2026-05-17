package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreationTimestamp
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;

    LocalDateTime deletedAt;

    //todo sessiondagi user id si tushadi
    Long createdBy;

    // todo sessiondagi update qivotgan user id si tushadi
    Long updatedBy;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    Boolean deleted;

    public void makeAsDeleted() {
        this.deletedAt = LocalDateTime.now();
        this.deleted = true;
    }
}
