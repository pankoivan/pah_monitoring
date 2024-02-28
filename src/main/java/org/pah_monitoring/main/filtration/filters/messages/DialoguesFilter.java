package org.pah_monitoring.main.filtration.filters.messages;

import org.pah_monitoring.main.entities.main.users.users.common.interfaces.User;
import org.pah_monitoring.main.filtration.enums.messages.DialoguesFiltrationProperty;
import org.pah_monitoring.main.filtration.enums.messages.DialoguesSortingProperty;
import org.pah_monitoring.main.filtration.filters.common.AbstractEntityFilter;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component("dialoguesFilter")
public class DialoguesFilter extends AbstractEntityFilter<User> {

    @Override
    public Stream<User> searched(List<User> recipients, String searching) {
        return searching == null || searching.isEmpty()
                ? recipients.stream()
                : recipients.stream().filter(
                        administrator -> administrator.getUserInformation().getFullName().toLowerCase().contains(searching.toLowerCase())
                );
    }

    @Override
    public Stream<User> filtered(Stream<User> recipients, String filtration) {
        Optional<DialoguesFiltrationProperty> filtrationProperty = DialoguesFiltrationProperty.optionalValueOf(filtration);
        return filtrationProperty.map(dialoguesFiltrationProperty -> switch (dialoguesFiltrationProperty) {
            case ACTIVE -> recipients.filter(User::isActive);
            case INACTIVE -> recipients.filter(User::isNotActive);
        }).orElse(recipients);
    }

    @Override
    public Stream<User> sorted(Stream<User> recipients, String sorting) {
        Optional<DialoguesSortingProperty> sortingProperty = DialoguesSortingProperty.optionalValueOf(sorting);
        return sortingProperty.map(dialoguesSortingProperty -> switch (dialoguesSortingProperty) {
            case FULL_NAME -> recipients.sorted(Comparator.comparing(recipient -> recipient.getUserInformation().getFullName()));
            case PHONE_NUMBER -> recipients.sorted(Comparator.comparing(recipient -> recipient.getUserInformation().getPhoneNumber()));
            case ROLE -> recipients.sorted(Comparator.comparing(User::getRole));
        }).orElse(recipients);
    }

}
