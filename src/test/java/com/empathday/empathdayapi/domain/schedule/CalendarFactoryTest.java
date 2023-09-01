package com.empathday.empathdayapi.domain.schedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.DefaultCalendarInfo;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalendarFactoryTest {

    @DisplayName("2023년 9월 첫째주 정보를 생성합니다.")
    @Test
    void createOneWeekCalendar() {
        // given
        LocalDate currentDate = LocalDate.of(2023, 9, 1);

        // when
        List<DefaultCalendarInfo> result = CalendarFactory.createOneWeekCalendar(currentDate);

        // then
        assertThat(result).hasSize(7)
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
        List<DefaultCalendarInfo> result = CalendarFactory.createOneMonthCalendar(currentDate);

        // then
        assertThat(result).hasSize(31);
    }
}