package org.pah_monitoring.main.filtration.enums.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PatientSortingProperty {

    FULL_NAME("По ФИО"),

    PHONE_NUMBER("По номеру телефона");

    private final String alias;

    public static Optional<PatientSortingProperty> optionalValueOf(String sorting) {
        try {
            return Optional.of(PatientSortingProperty.valueOf(sorting));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
