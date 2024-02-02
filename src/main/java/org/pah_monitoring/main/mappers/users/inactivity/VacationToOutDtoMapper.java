package org.pah_monitoring.main.mappers.users.inactivity;

import org.pah_monitoring.main.dto.out.users.inactivity.VacationOutDto;
import org.pah_monitoring.main.entities.main.users.inactivity.Vacation;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("vacationMapper")
public class VacationToOutDtoMapper implements BaseEntityToOutDtoMapper<Vacation, VacationOutDto> {

    @Override
    public VacationOutDto map(Vacation vacation) {
        return VacationOutDto
                .builder()
                .formattedStartDate(vacation.getFormattedStartDate())
                .formattedEndDate(vacation.getFormattedEndDate())
                .comment(vacation.getComment())
                .build();
    }

}
