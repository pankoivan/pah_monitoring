package org.pah_monitoring.main.filtration.enums.messages;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum DialogueFiltrationProperty {

    AUTHOR("Автор"),

    RECIPIENT("Получатель");

    private final String alias;

    public static Optional<DialogueFiltrationProperty> optionalValueOf(String filtration) {
        try {
            return Optional.of(DialogueFiltrationProperty.valueOf(filtration));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
