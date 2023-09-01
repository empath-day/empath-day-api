package com.empathday.empathdayapi.domain.schedule.emotion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Emotion {

    SO_BAD("soBadImageUrl"),
    BAD("bdaImageUrl"),
    NORMAL("normalImageURl"),
    GOOD("goodImageUrl"),
    VERY_GOOD("veryGoodImageUrl")
    ;

    private final String emotionImageUrl;
}
