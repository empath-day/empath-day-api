package com.empathday.empathdayapi.domain.file;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String generatePreSignedUrl(
        S3Presigner presigner,
        String filename
    ) {
        PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(createPresignedUrlRequset(filename));

        return presignedRequest.url().toString();
    }

    private PutObjectPresignRequest createPresignedUrlRequset(String filename) {
        return PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(10))
            .putObjectRequest(createPresignedRequest(filename))
            .build();
    }

    private PutObjectRequest createPresignedRequest(String filename) {
        return PutObjectRequest.builder()
            .bucket(bucket)
            .key(filename)
            .contentType("test/plain")
            .build();
    }
}
