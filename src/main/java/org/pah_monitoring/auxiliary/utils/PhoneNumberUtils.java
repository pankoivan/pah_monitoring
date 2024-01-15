package org.pah_monitoring.auxiliary.utils;

import lombok.experimental.UtilityClass;
import org.pah_monitoring.main.exceptions.utils.PhoneNumberUtilsException;

@UtilityClass
public final class PhoneNumberUtils {

    public static String readable(String phoneNumber) throws PhoneNumberUtilsException {
        if (!phoneNumber.matches("^\\d{11}$")) {
            throw new PhoneNumberUtilsException(
                    "Исходный номер телефона должен состоять только из подряд идущих цифр от 0 до 9. Пример: 89112345129"
            );
        }
        return "+%s (%s) %s-%s-%s".formatted(
                phoneNumber.substring(0, 1),
                phoneNumber.substring(1, 4),
                phoneNumber.substring(4, 7),
                phoneNumber.substring(7, 9),
                phoneNumber.substring(9, 11)
        );
    }

}
