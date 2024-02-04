package org.pah_monitoring.main.filtration.enums.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Optional;

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

    public static EnumSet<DoctorFiltrationProperty> subset() {
        return EnumSet.of(ACTIVE, VACATION, SICK_LEAVE, DISMISSAL);
    }

    public static Optional<DoctorFiltrationProperty> optionalValueOf(String filtration) {
        try {
            return Optional.of(DoctorFiltrationProperty.valueOf(filtration));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
