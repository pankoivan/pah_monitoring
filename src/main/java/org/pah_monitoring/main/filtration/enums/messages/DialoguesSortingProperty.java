package org.pah_monitoring.main.filtration.enums.messages;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum DialoguesSortingProperty {

    FULL_NAME("ФИО"),

    PHONE_NUMBER("Номер телефона"),

    ROLE("Роль");

    private final String alias;

    public static Optional<DialoguesSortingProperty> optionalValueOf(String filtration) {
        try {
            return Optional.of(DialoguesSortingProperty.valueOf(filtration));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
