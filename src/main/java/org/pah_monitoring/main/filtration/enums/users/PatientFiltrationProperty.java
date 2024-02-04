package org.pah_monitoring.main.filtration.enums.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PatientFiltrationProperty {

    ACTIVE("Активные"),

    INACTIVE("Неактивные");

    private final String alias;

}
