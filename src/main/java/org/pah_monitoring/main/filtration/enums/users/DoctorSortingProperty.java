package org.pah_monitoring.main.filtration.enums.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum DoctorSortingProperty {

    FULL_NAME("По ФИО"),

    PHONE_NUMBER("По номеру телефона"),

    PATIENTS_COUNT("По количеству пациентов");

    private final String alias;

    private EnumSet<DoctorSortingProperty> subsetForMainAdministrator() {
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
