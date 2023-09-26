package com.empathday.empathdayapi.interfaces.file;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.empathday.empathdayapi.config.AWSS3Config;
import com.empathday.empathdayapi.domain.file.FileUploadService;
import com.empathday.empathdayapi.domain.schedule.ScheduleService;
import com.empathday.empathdayapi.interfaces.file.FileUploadController;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(controllers = {
    FileUploadController.class
})
class FileUploadControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FileUploadService fileUploadService;
    @MockBean
    private AWSS3Config awss3Config;
    @MockBean
    private ScheduleService scheduleService;

    @BeforeEach
    void setUp(WebApplicationContext applicationContext) {
        mockMvc =
            MockMvcBuilders.webAppContextSetup(applicationContext)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .build();
    }

    @DisplayName("AWS Pre-signed Url 정보를 생성한다.")
    @Test
    void generatePresignedUrl() throws Exception {
        // given
        String filename = "empathyday.jpg";
        String presignedUrl = "hello";
        long imageId = 1L;
        when(fileUploadService.generatePreSignedUrl(awss3Config.s3Presigner() ,filename)).thenReturn(presignedUrl);
        when(scheduleService.createScheduleImage(filename)).thenReturn(imageId);



        // when && then
        mockMvc.perform(
                get("/api/file/v1/presigned-url")
                    .param("filename", filename)
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.presignedUrl").value(presignedUrl))
            .andExpect(jsonPath("$.message").doesNotExist())
            .andExpect(jsonPath("$.errorCode").doesNotExist());
    }
}