package org.pah_monitoring.main.mappers.users.inactivity;

import org.pah_monitoring.main.dto.out.users.inactivity.PatientInactivityOutDto;
import org.pah_monitoring.main.entities.main.users.inactivity.PatientInactivity;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("patientInactivityMapper")
public class PatientInactivityToOutDtoMapper implements BaseEntityToOutDtoMapper<PatientInactivity, PatientInactivityOutDto> {

    @Override
    public PatientInactivityOutDto map(PatientInactivity patientInactivity) {
        return PatientInactivityOutDto
                .builder()
                .activityMessage("Переведён в неактивное состояние %s".formatted(patientInactivity.getFormattedDate()))
                .authorMessagePart("Кем переведён:")
                .authorFullName(patientInactivity.getAuthor().getUserInformation().getFullName())
                .authorUserInformationId(patientInactivity.getAuthor().getUserInformation().getId())
                .formattedDate(patientInactivity.getFormattedDate())
                .comment(patientInactivity.getComment())
                .build();
    }

}
