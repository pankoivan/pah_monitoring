package org.pah_monitoring.main.filtration.enums.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PatientFiltrationProperty {

    ACTIVE("Активные"),

    INACTIVE("Неактивные");

    private final String alias;

    public static Optional<PatientFiltrationProperty> optionalValueOf(String filtration) {
        try {
            return Optional.of(PatientFiltrationProperty.valueOf(filtration));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
