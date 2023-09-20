package com.empathday.empathdayapi.infrastructure.schedule.comment;

import com.empathday.empathdayapi.domain.common.DeleteStatus;
import com.empathday.empathdayapi.domain.schedule.comment.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findByScheduleIdAndAndUserId(Long scheduleId, Long userId);

    @Query("select c from Comment c left join fetch c.parentComment where c.id = :id")
    Optional<Comment> findCommentByIdWithParent(@Param("id") Long id);

    Optional<Comment> findByIdAndDeleteStatus(Long id, DeleteStatus deleteStatus);
}
