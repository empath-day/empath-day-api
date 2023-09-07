package com.empathday.empathdayapi.interfaces.schedule;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.empathday.empathdayapi.domain.feed.like.ScheduleLikeService;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleLikeDto.CommandScheduleLikeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(controllers = {
    ScheduleLikeController.class
})
class ScheduleLikeControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ScheduleLikeService scheduleLikeService;

    @BeforeEach
    void setUp(WebApplicationContext applicationContext) {
        mockMvc =
            MockMvcBuilders.webAppContextSetup(applicationContext)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .build();
    }

    @DisplayName("스케줄 좋아요")
    @Test
    void likeSchedule() throws Exception {
        // given
        Long userId = 1L;
        Long feedId = 1L;

        CommandScheduleLikeRequest request = CommandScheduleLikeRequest.of(userId, feedId);

        // when // then
        mockMvc.perform(
                put("/api/v1/schedule/like")
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

    @DisplayName("스케줄 좋아요 취소")
    @Test
    void unLikeSchedule() throws Exception {
        // given
        Long userId = 1L;
        Long feedId = 1L;

        CommandScheduleLikeRequest request = CommandScheduleLikeRequest.of(userId, feedId);

        // when // then
        mockMvc.perform(
                put("/api/v1/schedule/unlike")
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