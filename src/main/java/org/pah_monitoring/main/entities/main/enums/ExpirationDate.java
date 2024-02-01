package org.pah_monitoring.main.entities.main.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ExpirationDate {

    ONE_DAY("1 день", 1),

    THREE_DAYS("3 дня", 3),

    WEEK("Неделя", 7),

    MONTH("Месяц", 30);

    private final String alias;

    private final Integer days;

}
