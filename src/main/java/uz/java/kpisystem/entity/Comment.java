package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment  extends Auditable {

    private String text;

    private Long authorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")  // commentlani comentRepository.findAll() qilganda N+1 issue
//            Agar 100 ta comment bo‘lsa:
//            1 ta query comment uchun
//            100 ta query task uchun
//            ✔️ Natija: 101 query = N+1 problem

//    yechimi: join fetch yani repostory da @Query("SELECT c FROM Comment c JOIN FETCH c.task")
//                         List<Comment> findAllWithTask();


    private Task task;

    private Long parentId;
}
