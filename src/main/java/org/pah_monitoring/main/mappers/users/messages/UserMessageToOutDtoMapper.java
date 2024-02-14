package org.pah_monitoring.main.mappers.users.messages;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.out.users.messages.UserMessageOutDto;
import org.pah_monitoring.main.entities.main.users.messages.UserMessage;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Component("userMessageMapper")
public class UserMessageToOutDtoMapper implements BaseEntityToOutDtoListMapper<UserMessage, UserMessageOutDto> {

    private CurrentUserCheckService checkService;

    @Override
    public UserMessageOutDto map(UserMessage userMessage) {
        return UserMessageOutDto
                .builder()
                .id(userMessage.getId())
                .author(userMessage.getAuthor())
                .authorFullName(checkService.isSelf(userMessage.getAuthor()) ? "Вы" : userMessage.getAuthor().getFullName())
                .recipient(userMessage.getRecipient().getFullName())
                .text(userMessage.getText())
                .date(userMessage.getDate())
                .formattedDate(userMessage.getFormattedDate())
                .editingDate(userMessage.getEditingDate())
                .formattedEditingDate(userMessage.getFormattedEditingDate())
                .build();
    }

}
