package uz.java.kpisystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comment_mentions")
public class CommentMention   extends Auditable {


    private Long commentId;

    private Long mentionedUserId;

}
