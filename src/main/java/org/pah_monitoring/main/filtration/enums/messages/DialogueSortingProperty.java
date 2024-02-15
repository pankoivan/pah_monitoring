package org.pah_monitoring.main.filtration.enums.messages;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum DialogueSortingProperty {

    DATE("Дата отправки"),

    EDITING_DATE("Дата редактирования");

    private final String alias;

    public static Optional<DialogueSortingProperty> optionalValueOf(String filtration) {
        try {
            return Optional.of(DialogueSortingProperty.valueOf(filtration));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
