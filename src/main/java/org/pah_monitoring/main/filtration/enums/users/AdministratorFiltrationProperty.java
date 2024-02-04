package org.pah_monitoring.main.filtration.enums.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AdministratorFiltrationProperty {

    ACTIVE("Активные"),

    VACATION("В отпуске"),

    SICK_LEAVE("На больничном"),

    DISMISSAL("Уволенные");

    private final String alias;

}
