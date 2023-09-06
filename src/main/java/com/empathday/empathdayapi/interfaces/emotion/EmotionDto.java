package com.empathday.empathdayapi.interfaces.emotion;

import com.empathday.empathdayapi.domain.schedule.emotion.EmotionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class EmotionDto {

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class EmotionRetrieveResponse {

        private String name;
        private String imageUrl;

        public static EmotionRetrieveResponse toDto(EmotionEntity emotion) {
            return EmotionRetrieveResponse.of(emotion.getEmotion().name(), emotion.getEmotion().getEmotionImageUrl());
        }
    }
}
