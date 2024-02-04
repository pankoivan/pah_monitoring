package org.pah_monitoring.main.filtration.enums.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum DoctorFiltrationProperty {

    ACTIVE("Активные"),

    VACATION("В отпуске"),

    SICK_LEAVE("На больничном"),

    DISMISSAL("Уволенные"),

    HAS_PATIENTS("С пациентами"),

    HAS_NO_PATIENTS("Без пациентов");

    private final String alias;

}
