package org.pah_monitoring.main.filtration.enums.messages;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum DialoguesFiltrationProperty {

    ACTIVE("Активные"),

    INACTIVE("Неактивные");

    private final String alias;

    public static Optional<DialoguesFiltrationProperty> optionalValueOf(String filtration) {
        try {
            return Optional.of(DialoguesFiltrationProperty.valueOf(filtration));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
