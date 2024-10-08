package org.pah_monitoring.main.filtration.enums.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum DoctorSortingProperty {

    FULL_NAME("ФИО"),

    PHONE_NUMBER("Номер телефона"),

    PATIENTS_COUNT("Количество пациентов");

    private final String alias;

    public static EnumSet<DoctorSortingProperty> subset() {
        return EnumSet.of(FULL_NAME, PHONE_NUMBER);
    }

    public static Optional<DoctorSortingProperty> optionalValueOf(String sorting) {
        try {
            return Optional.of(DoctorSortingProperty.valueOf(sorting));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
