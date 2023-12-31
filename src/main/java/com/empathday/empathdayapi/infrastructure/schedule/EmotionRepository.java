package com.empathday.empathdayapi.infrastructure.schedule;

import com.empathday.empathdayapi.domain.schedule.emotion.EmotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRepository extends JpaRepository<EmotionEntity, Long> {

}
