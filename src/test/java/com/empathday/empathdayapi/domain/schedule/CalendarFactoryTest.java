package com.empathday.empathdayapi.domain.schedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.DefaultCalendarInfo;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RetrieveScheduleMainResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalendarFactoryTest {

    @DisplayName("2023년 9월 첫째주 정보를 생성합니다.")
    @Test
    void createOneWeekCalendar() {
        // given
        LocalDate currentDate = LocalDate.of(2023, 9, 1);

        // when
        List<RetrieveScheduleMainResponse> result = CalendarFactory.createOneWeekCalendar(currentDate);
        List<DefaultCalendarInfo> calendarInfos = result.stream()
            .map(RetrieveScheduleMainResponse::getCalendarInfo)
            .collect(Collectors.toList());

        // then
        assertThat(calendarInfos).hasSize(7)
            .extracting("date", "day")
            .containsExactly(
                tuple(LocalDate.of(2023, 8, 28), DayOfWeek.MONDAY),
                tuple(LocalDate.of(2023, 8, 29), DayOfWeek.TUESDAY),
                tuple(LocalDate.of(2023, 8, 30), DayOfWeek.WEDNESDAY),
                tuple(LocalDate.of(2023, 8, 31), DayOfWeek.THURSDAY),
                tuple(LocalDate.of(2023, 9, 1),  DayOfWeek.FRIDAY),
                tuple(LocalDate.of(2023, 9, 2),  DayOfWeek.SATURDAY),
                tuple(LocalDate.of(2023, 9, 3),  DayOfWeek.SUNDAY)
            );
    }

    @DisplayName("2023년 9월 한달 정보를 생성합니다.")
    @Test
    void createOneMonthCalendar() {
        // given
        LocalDate currentDate = LocalDate.of(2023, 9, 1);

        // when
        List<RetrieveScheduleMainResponse> oneMonthCalendar = CalendarFactory.createOneMonthCalendar(currentDate);
        List<DefaultCalendarInfo> result = oneMonthCalendar.stream()
            .map(RetrieveScheduleMainResponse::getCalendarInfo)
            .collect(Collectors.toList());

        // then
        assertThat(result).hasSize(31);
    }

    @DisplayName("9월의 마지막 날짜를 구합니다.")
    @Test
    void getLastDateOfMonth() {
        // given
        LocalDate now = LocalDate.of(2023, 9, 5);

        // when
        LocalDate lastDateOfMonth = CalendarFactory.getLastDateOfMonth(now);

        // then
        assertThat(lastDateOfMonth).isEqualTo(LocalDate.of(2023, 9, 30));
    }
}