package org.pah_monitoring.main.filtration.enums.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AdministratorFiltrationProperty {

    ACTIVE("Активные"),

    VACATION("В отпуске"),

    SICK_LEAVE("На больничном"),

    DISMISSAL("Уволенные");

    private final String alias;

    public static Optional<AdministratorFiltrationProperty> optionalValueOf(String filtration) {
        try {
            return Optional.of(AdministratorFiltrationProperty.valueOf(filtration));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
