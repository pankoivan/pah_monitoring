package org.pah_monitoring.main.entities.main.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum YesNo {

    YES("Да", true),

    NO("Нет", false);

    private final String alias;

    private final boolean value;

}
