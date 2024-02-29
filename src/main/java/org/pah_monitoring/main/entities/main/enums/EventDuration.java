package org.pah_monitoring.main.entities.main.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EventDuration {

    SECONDS("Секунды"),

    MINUTES("Минуты"),

    HOURS("Часы"),

    DAYS("Дни"),

    HALF_HOUR("Полчаса"),

    HOUR("Час"),

    MORE("Больше");

    private final String alias;

    public static EnumSet<EventDuration> forChestPain() {
        return EnumSet.of(MINUTES, HOURS, DAYS);
    }

    public static EnumSet<EventDuration> forFainting() {
        return EnumSet.of(MINUTES, HALF_HOUR, HOUR, MORE);
    }

    public static EnumSet<EventDuration> forVertigo() {
        return EnumSet.of(SECONDS, MINUTES, HALF_HOUR, HOUR, MORE);
    }

}
