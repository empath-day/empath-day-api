package com.empathday.empathdayapi.interfaces.file;

import static com.amazonaws.HttpMethod.PUT;

import com.empathday.empathdayapi.domain.file.FileUploadService;
import com.empathday.empathdayapi.domain.schedule.ScheduleService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FileUploadController {

    private final FileUploadService fileUploadService;
    private final ScheduleService scheduleService;

    @GetMapping("/api/file/v1/presigned-url")
    public ResponseEntity<HashMap<String, Object>> generatePresignedUrl(
        @RequestParam("filename") String filename
    ) {
        String presignedUrl = fileUploadService.generatePreSignedUrl(filename, PUT);

        Long imageId = scheduleService.createScheduleImage(filename);

        HashMap<String, Object> map = new HashMap<>();
        map.put("presignedUrl", presignedUrl);
        map.put("imageId", imageId);

        return ResponseEntity.ok(map);
    }
}
