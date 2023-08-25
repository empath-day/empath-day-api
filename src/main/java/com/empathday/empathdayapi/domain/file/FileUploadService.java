package com.empathday.empathdayapi.domain.file;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import java.util.Calendar;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String generatePreSignedUrl(
        String filePath,
        HttpMethod httpMethod
    ) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10);

        return amazonS3.generatePresignedUrl(bucket, filePath, calendar.getTime(), httpMethod).toString();
    }
}
