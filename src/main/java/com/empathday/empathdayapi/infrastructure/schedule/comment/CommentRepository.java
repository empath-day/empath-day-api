package com.empathday.empathdayapi.infrastructure.schedule.comment;

import com.empathday.empathdayapi.domain.schedule.comment.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findByScheduleIdAndAndUserId(Long scheduleId, Long userId);
}
