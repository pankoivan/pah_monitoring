package org.pah_monitoring.main.entities.main.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum TrueFalseEnum {

    YES("Да", "Есть", true),

    NO("Нет", "Нет", false);

    private final String yesNo;

    private final String hasNot;

    private final boolean value;

}
