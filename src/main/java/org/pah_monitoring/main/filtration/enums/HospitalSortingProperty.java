package org.pah_monitoring.main.filtration.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum HospitalSortingProperty {

    NAME("Название"),

    OID("OID"),

    DATE("Дата");

    private final String alias;

}
