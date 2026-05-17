package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "comment_mentions")
public class CommentMention extends Auditable {

    private Long commentId;

    private Long mentionedUserId;

}
