package org.pah_monitoring.main.mappers.users.inactivity.common;

import org.pah_monitoring.main.dto.out.users.inactivity.common.InactivityOutDto;
import org.pah_monitoring.main.entities.main.users.inactivity.common.interfaces.Inactivity;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("inactivityMapper")
public class InactivityToOutDtoMapper implements BaseEntityToOutDtoMapper<Inactivity, InactivityOutDto> {

    @Override
    public InactivityOutDto map(Inactivity inactivity) {
        return InactivityOutDto
                .builder()
                .inactivityMessage(inactivity.getInactivityMessage())
                .authorMessagePart(inactivity.getAuthorMessagePart())
                .authorFullName(inactivity.getAuthorFullName())
                .authorUserInformationId(inactivity.getAuthorUserInformationId())
                .comment(inactivity.getComment())
                .build();
    }

}
