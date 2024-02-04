package org.pah_monitoring.main.filtration.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum HospitalFiltrationProperty {

    WAITING_CODE("Ожидает выдачи кода"),

    WAITING_REGISTRATION("Ожидает регистрации администратора"),

    REGISTERED("Зарегистрировано");

    private final String alias;

    public static Optional<HospitalFiltrationProperty> optionalValueOf(String filtration) {
        try {
            return Optional.of(HospitalFiltrationProperty.valueOf(filtration));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
