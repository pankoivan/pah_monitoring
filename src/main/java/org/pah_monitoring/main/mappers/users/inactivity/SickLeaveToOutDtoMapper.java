package org.pah_monitoring.main.mappers.users.inactivity;

import org.pah_monitoring.main.dto.out.users.inactivity.SickLeaveOutDto;
import org.pah_monitoring.main.entities.main.users.inactivity.SickLeave;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("sickLeaveMapper")
public class SickLeaveToOutDtoMapper implements BaseEntityToOutDtoMapper<SickLeave, SickLeaveOutDto> {

    @Override
    public SickLeaveOutDto map(SickLeave sickLeave) {
        return SickLeaveOutDto
                .builder()
                .formattedStartDate(sickLeave.getFormattedStartDate())
                .formattedEndDate(sickLeave.getFormattedEndDate())
                .comment(sickLeave.getComment())
                .build();
    }

}
