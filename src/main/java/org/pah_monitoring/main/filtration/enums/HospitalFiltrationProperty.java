package org.pah_monitoring.main.filtration.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum HospitalFiltrationProperty {

    WAITING_CODE("Ожидает выдачи кода"),

    WAITING_REGISTRATION("Ожидает регистрации администратора"),

    REGISTERED("Зарегистрировано");

    private final String alias;

}
