package org.pah_monitoring.main.filtration.enums.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AdministratorSortingProperty {

    FULL_NAME("ФИО"),

    PHONE_NUMBER("Номер телефона");

    private final String alias;

    public static Optional<AdministratorSortingProperty> optionalValueOf(String sorting) {
        try {
            return Optional.of(AdministratorSortingProperty.valueOf(sorting));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
