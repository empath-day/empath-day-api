package com.empathday.empathdayapi.interfaces.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class FileDto {

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class PresignedUrlResponse {

        private String presignedUrl;
        private Long imageId;
    }
}
