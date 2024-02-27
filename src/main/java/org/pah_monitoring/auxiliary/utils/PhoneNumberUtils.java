package org.pah_monitoring.auxiliary.utils;

import lombok.experimental.UtilityClass;
import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.exceptions.utils.PhoneNumberUtilsException;

@UtilityClass
public final class PhoneNumberUtils {

    @NullWhenNull
    public static String toReadable(String sourcePhoneNumber) throws PhoneNumberUtilsException {
        if (!sourcePhoneNumber.matches("^\\d{11}$")) {
            throw new PhoneNumberUtilsException(
                    "Исходный номер телефона должен состоять только из подряд идущих цифр от 0 до 9. Пример: 89112345129"
            );
        }
        return "+%s (%s) %s-%s-%s".formatted(
                sourcePhoneNumber.substring(0, 1),
                sourcePhoneNumber.substring(1, 4),
                sourcePhoneNumber.substring(4, 7),
                sourcePhoneNumber.substring(7, 9),
                sourcePhoneNumber.substring(9, 11)
        );
    }

    @NullWhenNull
    public static String toSource(String readablePhoneNumber) {
        return readablePhoneNumber.replaceAll("[^0-9]", "");
    }

}
