package com.empathday.empathdayapi.domain.schedule;

import static com.empathday.empathdayapi.common.response.ErrorCode.REQUIRED_EMOTION;

import com.empathday.empathdayapi.common.exception.InvalidParamException;
import com.empathday.empathdayapi.domain.schedule.scheduleimage.ScheduleImage;
import com.empathday.empathdayapi.domain.schedule.todo.Todo;
import com.empathday.empathdayapi.exception.feed.FeedNotFoundException;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleImageRepository;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleRepository;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleRequest;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RetrieveScheduleDetailMainResponse;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RetrieveScheduleMainResponse;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RetrieveScheduleResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
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

    /**
     * 스케쥴 이미지를 등록합니다.
     *
     * @param filename
     * @return
     */
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
        if (request.isEmotionBlank()) {
            throw new InvalidParamException(REQUIRED_EMOTION);
        }

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
    public RetrieveScheduleDetailMainResponse retrieveScheduleDetail(Long id, Long userId) {
        Schedule findSchedule = getScheduleByFeedIdAndUserId(id, userId);

        return RetrieveScheduleDetailMainResponse.of(RetrieveScheduleResponse.fromEntity(findSchedule));
    }

    private Schedule getScheduleByFeedIdAndUserId(Long id, Long userId) {
        return scheduleRepository.findByIdAndUserId(id, userId).orElseThrow(
            () -> new FeedNotFoundException()
        );
    }

    /**
     * 사용자의 1주일치 스케줄 정보를 조회합니다.
     *
     * @return
     */
    public List<RetrieveScheduleMainResponse> retrieveOneWeekScheduleInfo(Long userId) {
        LocalDate currentDate = LocalDate.now();

        LocalDate monday = currentDate.with(DayOfWeek.MONDAY);
        LocalDate sunday = currentDate.with(DayOfWeek.SUNDAY);

        List<Schedule> findSchedule = scheduleRepository.findAllByUserIdAndScheduleDateBetween(userId, monday, sunday);

        return CalendarFactory.createOneWeekCalendarForUser(currentDate, findSchedule);
    }

    /**
     * 사용자의 1달치 스케줄 정보를 조회합니다.
     *
     * @return
     */
    public List<RetrieveScheduleMainResponse> retrieveOneMonthScheduleInfo(Long userId) {
        LocalDate currentDate = LocalDate.now();

        LocalDate monthStart = CalendarFactory.getFirstDateOfMonth(currentDate);
        LocalDate monthLast = CalendarFactory.getLastDateOfMonth(currentDate);

        List<Schedule> findSchedule = scheduleRepository.findAllByUserIdAndScheduleDateBetween(userId, monthStart, monthLast);

        return CalendarFactory.createOneMonthCalendarForUser(currentDate, findSchedule);
    }

    public Schedule getScheduleInfo(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
            () -> new FeedNotFoundException()
        );
    }
}
