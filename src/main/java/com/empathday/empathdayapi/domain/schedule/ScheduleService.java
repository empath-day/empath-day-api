package com.empathday.empathdayapi.domain.schedule;

import com.empathday.empathdayapi.common.exception.InvalidParamException;
import com.empathday.empathdayapi.domain.schedule.scheduleimage.ScheduleImage;
import com.empathday.empathdayapi.domain.schedule.todo.Todo;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleImageRepository;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleRepository;
import com.empathday.empathdayapi.infrastructure.schedule.todo.TodoRepository;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
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
}
