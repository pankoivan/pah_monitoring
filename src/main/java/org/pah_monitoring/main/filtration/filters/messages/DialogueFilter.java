package org.pah_monitoring.main.filtration.filters.messages;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.out.users.messages.UserMessageOutDto;
import org.pah_monitoring.main.filtration.enums.messages.DialogueFiltrationProperty;
import org.pah_monitoring.main.filtration.enums.messages.DialogueSortingProperty;
import org.pah_monitoring.main.filtration.filters.common.AbstractEntityFilter;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Component("dialogueFilter")
public class DialogueFilter extends AbstractEntityFilter<UserMessageOutDto> {

    private CurrentUserCheckService checkService;

    @Override
    public Stream<UserMessageOutDto> searched(List<UserMessageOutDto> messages, String searching) {
        return searching == null || searching.isEmpty()
                ? messages.stream()
                : messages.stream().filter(message -> message.getText().toLowerCase().contains(searching.toLowerCase()));
    }

    @Override
    public Stream<UserMessageOutDto> filtered(Stream<UserMessageOutDto> messages, String filtration) {
        Optional<DialogueFiltrationProperty> filtrationProperty = DialogueFiltrationProperty.optionalValueOf(filtration);
        return filtrationProperty.map(dialogueFiltrationProperty -> switch (dialogueFiltrationProperty) {
            case AUTHOR -> messages.filter(message -> checkService.isSelf(message.getAuthor()));
            case RECIPIENT -> messages.filter(message -> !checkService.isSelf(message.getAuthor()));
        }).orElse(messages);
    }

    @Override
    public Stream<UserMessageOutDto> sorted(Stream<UserMessageOutDto> messages, String sorting) {
        Optional<DialogueSortingProperty> sortingProperty = DialogueSortingProperty.optionalValueOf(sorting);
        return sortingProperty.map(dialogueSortingProperty -> switch (dialogueSortingProperty) {
            case DATE -> messages.sorted(Comparator.comparing(UserMessageOutDto::getDate));
            case EDITING_DATE -> messages.sorted(Comparator.comparing(
                    UserMessageOutDto::getEditingDate,
                    Comparator.nullsLast(Comparator.naturalOrder())
            ));
        }).orElse(messages);
    }

}
