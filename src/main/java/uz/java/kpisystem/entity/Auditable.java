package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    //todo sessiondagi user id si tushadi
    private Long createdBy;

    // todo sessiondagi update qivotgan user id si tushadi
    private Long updatedBy;

    @Column(columnDefinition = "DEFAULT BOOLEAN FALSE")
    private Boolean deleted;

    public void makeAsDeleted() {
        this.deletedAt = LocalDateTime.now();
        this.deleted = true;
    }
}
