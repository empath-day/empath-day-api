package com.empathday.empathdayapi.domain.schedule.emotion;

import java.util.Arrays;
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

    public static Emotion isAvailable(Emotion emotion) {
        return Arrays.stream(Emotion.values())
            .filter(e -> e == emotion)
            .findAny()
            .orElse(null);
    }
}
