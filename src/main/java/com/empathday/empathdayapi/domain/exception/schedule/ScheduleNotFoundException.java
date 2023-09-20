package com.empathday.empathdayapi.domain.exception.schedule;

import com.empathday.empathdayapi.common.exception.BaseException;
import com.empathday.empathdayapi.common.response.ErrorCode;

public class ScheduleNotFoundException extends BaseException {

    public ScheduleNotFoundException() {
        super(ErrorCode.SCHEDULE_ENTITY_NOT_FOUND);
    }
}
