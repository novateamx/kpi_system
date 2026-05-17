package uz.java.kpisystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "comments")
public class Comment extends Auditable {

    private String text;

    private Long authorId;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private Long parentId;
}
