package org.pah_monitoring.main.filtration.enums.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AdministratorSortingProperty {

    FULL_NAME("По ФИО"),

    PHONE_NUMBER("По номеру телефона");

    private final String alias;

}
