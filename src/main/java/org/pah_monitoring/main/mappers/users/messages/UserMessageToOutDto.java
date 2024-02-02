package org.pah_monitoring.main.mappers.users.messages;

import org.pah_monitoring.main.dto.out.users.messages.UserMessageOutDto;
import org.pah_monitoring.main.entities.main.users.messages.UserMessage;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("userMessageMapper")
public class UserMessageToOutDto implements BaseEntityToOutDtoMapper<UserMessage, UserMessageOutDto> {

    @Override
    public UserMessageOutDto map(UserMessage userMessage) {
        return UserMessageOutDto
                .builder()
                .author("Вы")
                .recipient(userMessage.getRecipient().getFullName())
                .text(userMessage.getText())
                .formattedDate(userMessage.getFormattedDate())
                .formattedEditingDate(userMessage.getFormattedEditingDate())
                .build();
    }

}
