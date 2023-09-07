package com.empathday.empathdayapi.infrastructure.schedule.emotion;

import com.empathday.empathdayapi.domain.emotion.emotion.EmotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRepository extends JpaRepository<EmotionEntity, Long> {

}
