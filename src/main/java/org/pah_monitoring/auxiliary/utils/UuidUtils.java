package org.pah_monitoring.auxiliary.utils;

import lombok.experimental.UtilityClass;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;

import java.util.UUID;

@UtilityClass
public final class UuidUtils {

    public static UUID fromString(String stringUuid) throws UuidUtilsException {
        try {
            return UUID.fromString(stringUuid);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new UuidUtilsException("Некорректный UUID: \"%s\"".formatted(stringUuid));
        }
    }

}
