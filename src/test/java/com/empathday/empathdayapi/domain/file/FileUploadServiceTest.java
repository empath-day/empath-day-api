package com.empathday.empathdayapi.domain.file;

import static org.assertj.core.api.Assertions.assertThat;

import com.empathday.empathdayapi.config.AWSS3Config;
import com.empathday.empathdayapi.domain.file.FileUploadService;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FileUploadServiceTest {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private AWSS3Config awss3Config;

    @DisplayName("preSignedUrl을 생성합니다.")
    @Test
    void generatePreSignedUrl() throws URISyntaxException, MalformedURLException {
        // given
        String filename = "upload.jpg";

        // when
        String preSignedUrl = fileUploadService.generatePreSignedUrl(awss3Config.s3Presigner(), filename);

        // then
        URL url = new URL(preSignedUrl);
        URI uri = url.toURI();

        assertThat(preSignedUrl).isNotBlank();
        assertThat(uri.getHost()).isEqualTo("empathy-day-img-bucket.s3.ap-northeast-2.amazonaws.com");
    }

    @DisplayName("s3 bean 값들을 검증합니다.")
    @Test
    void validateS3BeanProperties() {
        // given
        String accessKey = awss3Config.getAccessKey();
        String secretKey = awss3Config.getSecretKey();
        String region = awss3Config.getRegion();

        // when // then
        assertThat(accessKey).isEqualTo("AKIAZSQU7SAJQQGPH5XG");
        assertThat(secretKey).isEqualTo("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");
        assertThat(region).isEqualTo("ap-northeast-2");
    }
}