package com.empathday.empathdayapi.domain.schedule.comment;

import com.empathday.empathdayapi.domain.common.AbstractEntity;
import com.empathday.empathdayapi.domain.common.DeleteStatus;
import com.empathday.empathdayapi.domain.schedule.Schedule;
import com.empathday.empathdayapi.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "schedule_comments")
public class Comment extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @Builder.Default
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> childComment = new ArrayList<>();

    @Column(nullable = false)
    private String content;

    private Integer likeCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeleteStatus deleteStatus;

    public static Comment initReply(Comment parentComment, User writer, Schedule schedule, String content) {
        return Comment.builder()
            .userId(writer.getId())
            .schedule(schedule)
            .parentComment(parentComment)
            .content(content)
            .likeCount(0)
            .deleteStatus(DeleteStatus.N)
            .build();
    }

    public static Comment init(User writer, Schedule schedule, String content) {
        return Comment.builder()
            .userId(writer.getId())
            .schedule(schedule)
            .content(content)
            .likeCount(0)
            .deleteStatus(DeleteStatus.N)
            .build();
    }

    public boolean hasParentComment() {
        return this.parentComment != null ;
    }

    public void changeDeleteStatus() {
        this.deleteStatus = DeleteStatus.Y;
    }

    public void modify(String content) {
        this.content = content;
    }
}

