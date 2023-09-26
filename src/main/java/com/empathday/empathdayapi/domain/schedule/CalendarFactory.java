package com.empathday.empathdayapi.domain.schedule;

import static java.time.DayOfWeek.MONDAY;

import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.DefaultCalendarInfo;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RetrieveScheduleMainResponse;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RetrieveScheduleResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CalendarFactory {

    /**
     * 1주일 캘린더 정보를 생성합니다.
     *
     * @param currentDate
     * @return
     */
    public static List<RetrieveScheduleMainResponse> createOneWeekCalendar(LocalDate currentDate) {
        LocalDate mondayLocalDate = getMondayByCurrentDate(currentDate);

        List<RetrieveScheduleMainResponse> defaultData = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = mondayLocalDate.plusDays(i);

            defaultData.add(RetrieveScheduleMainResponse.of(new RetrieveScheduleResponse(), DefaultCalendarInfo.create(date, date.getDayOfWeek())));
        }

        return defaultData;
    }

    /** 현재 날짜를 기준으로 월요일의 요일 정보 계산 **/
    private static LocalDate getMondayByCurrentDate(LocalDate currentDate) {
        return currentDate.minusDays(currentDate.getDayOfWeek().getValue() - MONDAY.getValue());
    }

    public static List<RetrieveScheduleMainResponse> createOneMonthCalendar(LocalDate currentDate) {
        LocalDate firstDay = getFirstDateOfMonth(currentDate);

        List<RetrieveScheduleMainResponse> defaultData = new ArrayList<>();
        for (int i = 0; i < firstDay.lengthOfMonth(); i++) {
            defaultData.add(
                RetrieveScheduleMainResponse.of(new RetrieveScheduleResponse(), DefaultCalendarInfo.create(currentDate, currentDate.getDayOfWeek()))
            );
        }

        return defaultData;
    }

    public static LocalDate getFirstDateOfMonth(LocalDate currentDate) {
        return currentDate.minusMonths(1);
    }

    public static LocalDate getLastDateOfMonth(LocalDate currentDate) {
        return currentDate.withDayOfMonth(currentDate.lengthOfMonth());
    }

    /**
     * 사용자의 1주일치 스케줄 정보를 생성해서 반환합니다.
     *
     * @param currentDate
     * @param findSchedule
     * @return
     */
    public static List<RetrieveScheduleMainResponse> createOneWeekCalendarForUser(LocalDate currentDate, List<Schedule> findSchedule) {
        List<RetrieveScheduleMainResponse> oneWeekCalendar = createOneWeekCalendar(currentDate);

        Map<LocalDate, RetrieveScheduleMainResponse> map = getDateMap(oneWeekCalendar);

        for (Schedule notNullSchedule : filterHavingSchedule(findSchedule)) {
            RetrieveScheduleMainResponse mainResponse = map.get(notNullSchedule.getScheduleDate());

            mainResponse.mappingSchedule(RetrieveScheduleResponse.fromEntity(notNullSchedule));

            map.put(notNullSchedule.getScheduleDate(), mainResponse);
        }

        return map.values().stream().collect(Collectors.toList());
    }

    private static List<Schedule> filterHavingSchedule(List<Schedule> findSchedule) {
        return findSchedule.stream()
            .filter(schedule -> Objects.nonNull(schedule))
            .collect(Collectors.toList());
    }

    private static Map<LocalDate, RetrieveScheduleMainResponse> getDateMap(List<RetrieveScheduleMainResponse> oneWeekCalendar) {
        return oneWeekCalendar.stream()
            .collect(Collectors.toMap(calendar -> calendar.getCalendarInfo().getDate(), c -> c));
    }

    /**
     * 사용자의 1달치 스케줄 정보를 생성해서 반환합니다.
     *
     * @param currentDate
     * @param findSchedule
     * @return
     */
    public static List<RetrieveScheduleMainResponse> createOneMonthCalendarForUser(LocalDate currentDate, List<Schedule> findSchedule) {
        List<RetrieveScheduleMainResponse> oneMonthCalendar = createOneMonthCalendar(currentDate);

        Map<LocalDate, RetrieveScheduleMainResponse> map = getDateMap(oneMonthCalendar);

        for (Schedule notNullSchedule : filterHavingSchedule(findSchedule)) {
            RetrieveScheduleMainResponse mainResponse = map.get(notNullSchedule.getScheduleDate());

            mainResponse.mappingSchedule(RetrieveScheduleResponse.fromEntity(notNullSchedule));

            map.put(notNullSchedule.getScheduleDate(), mainResponse);
        }

        return map.values().stream().collect(Collectors.toList());
    }
}
