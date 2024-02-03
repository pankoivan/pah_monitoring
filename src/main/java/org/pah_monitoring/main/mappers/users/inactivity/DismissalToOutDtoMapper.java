package org.pah_monitoring.main.mappers.users.inactivity;

import org.pah_monitoring.main.dto.out.users.inactivity.DismissalOutDto;
import org.pah_monitoring.main.entities.main.users.inactivity.Dismissal;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("dismissalMapper")
public class DismissalToOutDtoMapper implements BaseEntityToOutDtoMapper<Dismissal, DismissalOutDto> {

    @Override
    public DismissalOutDto map(Dismissal dismissal) {
        return DismissalOutDto
                .builder()
                .activityMessage("Уволен %s".formatted(dismissal.getFormattedDate()))
                .authorMessagePart("Кем уволен:")
                .authorFullName(dismissal.getAuthor().getUserInformation().getFullName())
                .authorUserInformationId(dismissal.getAuthor().getUserInformation().getId())
                .formattedDate(dismissal.getFormattedDate())
                .comment(dismissal.getComment())
                .build();
    }

}
