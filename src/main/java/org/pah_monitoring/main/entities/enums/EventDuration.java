package org.pah_monitoring.main.entities.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EventDuration {

    SECONDS("Секунды"),

    MINUTES("Минуты"),

    HOURS("Часы"),

    DAYS("Дни"),

    FEW_MINUTES("Несколько минут"),

    HALF_HOUR("Полчаса"),

    HOUR("Час"),

    WHOLE_DAY("Целый день"),

    MORE("Больше");

    private final String alias;

}
