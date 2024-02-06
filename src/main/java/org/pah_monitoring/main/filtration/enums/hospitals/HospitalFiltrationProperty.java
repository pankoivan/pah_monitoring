package org.pah_monitoring.main.filtration.enums.hospitals;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum HospitalFiltrationProperty {

    WAITING_CODE("Ожидают выдачи кода"),

    WAITING_REGISTRATION("Ожидают регистрации администратора"),

    REGISTERED("Зарегистрированные");

    private final String alias;

    public static Optional<HospitalFiltrationProperty> optionalValueOf(String filtration) {
        try {
            return Optional.of(HospitalFiltrationProperty.valueOf(filtration));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
