package com.empathday.empathdayapi.domain.emotion.emotion;

import com.empathday.empathdayapi.infrastructure.schedule.emotion.EmotionRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Component
public class EmotionLoader {

    private final EmotionRepository emotionRepository;

    @PostConstruct
    public void initEmotion() {
        List<EmotionEntity> findEmotions = emotionRepository.findAll();
        if (CollectionUtils.isEmpty(findEmotions)) {
            for (Emotion emotion : Emotion.values()) {
                emotionRepository.save(new EmotionEntity(emotion));
            }
        }
    }
}
