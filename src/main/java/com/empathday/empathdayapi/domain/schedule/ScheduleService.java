package com.empathday.empathdayapi.domain.schedule;

import com.empathday.empathdayapi.infrastructure.schedule.ScheduleImageRepository;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleRepository;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleImageRepository scheduleImageRepository;

    public Long createScheduleImage(String filename) {
        ScheduleImage scheduleImage = new ScheduleImage();
        scheduleImage.setFilename(filename);

        scheduleImageRepository.save(scheduleImage);

        return scheduleImage.getId();
    }

    public void createSchedule(RegisterScheduleRequest request) {
        ScheduleImage scheduleImage = scheduleImageRepository.findById(request.getImageId()).orElseThrow(
            () -> new IllegalArgumentException("not found image.")
        );

        scheduleRepository.save(request.toEntity(scheduleImage));
    }
}
