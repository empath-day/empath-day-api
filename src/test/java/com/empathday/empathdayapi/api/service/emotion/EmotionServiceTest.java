package com.empathday.empathdayapi.api.service.emotion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.empathday.empathdayapi.domain.emotion.EmotionService;
import com.empathday.empathdayapi.interfaces.emotion.EmotionDto.EmotionRetrieveResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class EmotionServiceTest {

    @Autowired
    private EmotionService emotionService;

    @DisplayName("등록된 이모지 정보를 조회합니다.")
    @Test
    void getEmotions() {
        // given
        List<EmotionRetrieveResponse> result = emotionService.retrieveEmotions();

        // when then
        assertThat(result).hasSize(5)
            .extracting("name", "imageUrl")
            .containsExactlyInAnyOrder(
                tuple("SO_BAD","soBadImageUrl"),
                tuple("BAD","bdaImageUrl"),
                tuple("NORMAL","normalImageURl"),
                tuple("GOOD","goodImageUrl"),
                tuple("VERY_GOOD","veryGoodImageUrl")
                );
    }
}