package org.pah_monitoring.main.filtration.enums.messages;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum DialoguesSortingProperty {

    FULL_NAME("ФИО"),

    PHONE_NUMBER("Номер телефона");

    private final String alias;

    public static Optional<DialoguesSortingProperty> optionalValueOf(String sorting) {
        try {
            return Optional.of(DialoguesSortingProperty.valueOf(sorting));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
