package org.pah_monitoring.main.filtration.enums.hospitals;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum HospitalSortingProperty {

    NAME("Название"),

    OID("OID"),

    DATE("Дата");

    private final String alias;

    public static Optional<HospitalSortingProperty> optionalValueOf(String sorting) {
        try {
            return Optional.of(HospitalSortingProperty.valueOf(sorting));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
