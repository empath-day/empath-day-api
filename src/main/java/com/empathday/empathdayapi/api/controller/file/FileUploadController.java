package com.empathday.empathdayapi.api.controller.file;

import com.empathday.empathdayapi.common.response.CommonResponse;
import com.empathday.empathdayapi.config.AWSS3Config;
import com.empathday.empathdayapi.domain.file.FileUploadService;
import com.empathday.empathdayapi.domain.schedule.ScheduleService;
import com.empathday.empathdayapi.api.controller.file.FileDto.PresignedUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FileUploadController {

    private final FileUploadService fileUploadService;
    private final ScheduleService scheduleService;
    private final AWSS3Config awss3Config;

    @Operation(
        summary = "AWS pre-signedUrl 생성 API",
        description = "AWS pre-signed url을 생성하여 반환합니다."
    )
    @GetMapping("/api/file/v1/presigned-url")
    public CommonResponse<PresignedUrlResponse> generatePresignedUrl(
        @RequestParam("filename") String filename
    ) {
        String presignedUrl = fileUploadService.generatePreSignedUrl(awss3Config.s3Presigner(), filename);

        Long imageId = scheduleService.createScheduleImage(filename);

        return CommonResponse.success(PresignedUrlResponse.of(presignedUrl, imageId));
    }
}
