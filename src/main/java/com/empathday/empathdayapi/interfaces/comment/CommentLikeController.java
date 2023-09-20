package com.empathday.empathdayapi.interfaces.comment;

import com.empathday.empathdayapi.domain.schedule.comment.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentLikeController {

    private final CommentService commentService;
}
