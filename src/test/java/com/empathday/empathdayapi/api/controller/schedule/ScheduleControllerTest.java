package com.empathday.empathdayapi.api.controller.schedule;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.empathday.empathdayapi.domain.schedule.ScheduleService;
import com.empathday.empathdayapi.domain.schedule.emotion.Emotion;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleController;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(controllers = {
    ScheduleController.class
})
public class ScheduleControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ScheduleService scheduleService;

    @BeforeEach
    void setUp(WebApplicationContext applicationContext) {
        mockMvc =
            MockMvcBuilders.webAppContextSetup(applicationContext)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .build();
    }

    @DisplayName("스케쥴을 등록한다.")
    @Test
    void createSchedule() throws Exception {
        // given
        RegisterScheduleRequest request = RegisterScheduleRequest.builder()
            .userId(1L)
            .scheduleDate(LocalDate.of(2023, 8, 28))
            .title("헬로")
            .content("내용")
            .imageId(1L)
            .emotion(Emotion.GOOD)
            .isPublic(true)
            .todos(List.of("hihi", "hello"))
            .build();

        // when // then
        mockMvc.perform(
                post("/api/v1/schedule")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data").value("OK"))
            .andExpect(jsonPath("$.message").doesNotExist())
            .andExpect(jsonPath("$.errorCode").doesNotExist());
    }
}
