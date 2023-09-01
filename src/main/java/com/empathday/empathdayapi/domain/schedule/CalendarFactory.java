package com.empathday.empathdayapi.domain.schedule;

import static java.time.DayOfWeek.MONDAY;

import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.DefaultCalendarInfo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    public static List<DefaultCalendarInfo> createOneWeekCalendar(LocalDate currentDate) {
        LocalDate mondayLocalDate = getMondayByCurrentDate(currentDate);

        List<DefaultCalendarInfo> defaultData = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = mondayLocalDate.plusDays(i);

            defaultData.add(DefaultCalendarInfo.create(date, date.getDayOfWeek()));
        }

        return defaultData;
    }

    /** 현재 날짜를 기준으로 월요일의 요일 정보 계산 **/
    private static LocalDate getMondayByCurrentDate(LocalDate currentDate) {
        return currentDate.minusDays(currentDate.getDayOfWeek().getValue() - MONDAY.getValue());
    }

    public static List<DefaultCalendarInfo> createOneMonthCalendar(LocalDate currentDate) {
        LocalDate firstDay = getFirstDay(currentDate);

        List<DefaultCalendarInfo> defaultData = new ArrayList<>();
        for (int i = 0; i < firstDay.lengthOfMonth(); i++) {
            defaultData.add(DefaultCalendarInfo.create(currentDate, currentDate.getDayOfWeek()));
        }

        return defaultData;
    }

    private static LocalDate getLastDay(LocalDate firstDay) {
        return firstDay.withDayOfMonth(firstDay.lengthOfMonth());
    }

    private static LocalDate getFirstDay(LocalDate currentDate) {
        LocalDate firstDay = currentDate.minusMonths(1);
        return firstDay;
    }
}
