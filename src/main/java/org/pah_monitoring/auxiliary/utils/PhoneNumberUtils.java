package org.pah_monitoring.auxiliary.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class PhoneNumberUtils {

    public static String toReadable(String sourcePhoneNumber) {
        return "+%s (%s) %s-%s-%s".formatted(
                sourcePhoneNumber.substring(0, 1),
                sourcePhoneNumber.substring(1, 4),
                sourcePhoneNumber.substring(4, 7),
                sourcePhoneNumber.substring(7, 9),
                sourcePhoneNumber.substring(9, 11)
        );
    }

    public static String toSource(String readablePhoneNumber) {
        return readablePhoneNumber.replaceAll("[^0-9]", "");
    }

}
