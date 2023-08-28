package com.empathday.empathdayapi.api.domain.file;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.HttpMethod;
import com.empathday.empathdayapi.domain.file.FileUploadService;
import java.net.URISyntaxException;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FileUploadServiceTest {

    @Autowired
    private FileUploadService fileUploadService;

    @DisplayName("preSignedUrl을 생성합니다.")
    @Test
    void generatePreSignedUrl() throws URISyntaxException {
        // given
        String filename = "upload.jpg";

        // when
        String preSignedUrl = fileUploadService.generatePreSignedUrl(filename, HttpMethod.PUT);

        // then
        URIBuilder uriBuilder = new URIBuilder(preSignedUrl);

        assertThat(preSignedUrl).isNotBlank();
        assertThat(uriBuilder.getHost()).isEqualTo("empathy-day-img-bucket.s3.ap-northeast-2.amazonaws.com");
        assertThat(uriBuilder.getPathSegments().get(0)).isEqualTo(filename);
    }
}