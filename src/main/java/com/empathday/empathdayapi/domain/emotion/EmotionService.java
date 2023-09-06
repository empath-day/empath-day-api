package com.empathday.empathdayapi.domain.emotion;

import com.empathday.empathdayapi.domain.schedule.emotion.EmotionEntity;
import com.empathday.empathdayapi.infrastructure.schedule.EmotionRepository;
import com.empathday.empathdayapi.interfaces.emotion.EmotionDto.EmotionRetrieveResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EmotionService {

    private final EmotionRepository emotionRepository;

    public List<EmotionRetrieveResponse> retrieveEmotions() {
        List<EmotionEntity> emotionList = getEmotionList();

        return emotionList.stream()
            .map(emotion -> EmotionRetrieveResponse.toDto(emotion))
            .collect(Collectors.toList());
    }

    private List<EmotionEntity> getEmotionList() {
        return emotionRepository.findAll();
    }
}
