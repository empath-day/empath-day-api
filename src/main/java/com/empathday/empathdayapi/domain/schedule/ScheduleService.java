package com.empathday.empathdayapi.domain.schedule;

import com.empathday.empathdayapi.common.exception.InvalidParamException;
import com.empathday.empathdayapi.domain.schedule.scheduleimage.ScheduleImage;
import com.empathday.empathdayapi.domain.schedule.todo.Todo;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleImageRepository;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleRepository;
import com.empathday.empathdayapi.infrastructure.schedule.todo.TodoRepository;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleRequest;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RetrieveScheduleMainResponse;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RetrieveScheduleResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleImageRepository scheduleImageRepository;
    private final TodoRepository todoRepository;

    public Long createScheduleImage(String filename) {
        ScheduleImage scheduleImage = ScheduleImage.toEntity(filename);

        scheduleImageRepository.save(scheduleImage);

        return scheduleImage.getId();
    }

    /**
     * 스케줄을 등록합니다.
     *
     * @param request
     * @return
     */
    @Transactional
    public Schedule createSchedule(RegisterScheduleRequest request) {
        ScheduleImage scheduleImage = null;
        if (request.imageExists()) {
            scheduleImage = scheduleImageRepository.findById(request.getImageId()).orElseThrow(
                () -> new InvalidParamException("이미지 정보를 찾지 못하였습니다.")
            );
        }

        Schedule schedule = request.toEntity(scheduleImage);

        if (request.todoExists()) {
            schedule.addScheduleTodos(requestToTodoList(request, schedule));
        }

        return scheduleRepository.save(schedule);
    }

    private static List<Todo> requestToTodoList(RegisterScheduleRequest request, Schedule schedule) {
        return request.getTodos().stream()
            .map(todoContent -> Todo.of(schedule, todoContent))
            .collect(Collectors.toList());
    }

    /**
     * 스케줄 상세 정보를 조회합니다.
     *
     * @param id    스케줄 ID
     */
    public RetrieveScheduleMainResponse retrieveScheduleDetail(Long id) {
        Schedule findSchedule = scheduleRepository.findById(id).orElseThrow(
            () -> new InvalidParamException()
        );

        return RetrieveScheduleMainResponse.of(RetrieveScheduleResponse.fromEntity(findSchedule));
    }
}
