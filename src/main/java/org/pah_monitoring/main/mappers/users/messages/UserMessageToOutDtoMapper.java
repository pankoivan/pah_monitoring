package org.pah_monitoring.main.mappers.users.messages;

import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.users.messages.UserMessageOutDto;
import org.pah_monitoring.main.entities.main.users.messages.UserMessage;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("userMessageMapper")
public class UserMessageToOutDtoMapper implements BaseEntityToOutDtoListMapper<UserMessage, UserMessageOutDto> {

    @Override
    @NullWhenNull
    public UserMessageOutDto map(UserMessage userMessage) {
        return UserMessageOutDto
                .builder()
                .id(userMessage.getId())
                .authorId(userMessage.getAuthor().getId())
                .authorFullName(userMessage.getAuthor().getFullName())
                .recipientId(userMessage.getRecipient().getId())
                .recipientFullName(userMessage.getRecipient().getFullName())
                .text(userMessage.getText())
                .date(userMessage.getDate())
                .formattedDate(userMessage.getFormattedDate())
                .editingDate(userMessage.getEditingDate())
                .formattedEditingDate(userMessage.getFormattedEditingDate())
                .build();
    }

}
