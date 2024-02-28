package org.pah_monitoring.auxiliary.utils;

import lombok.experimental.UtilityClass;
import org.pah_monitoring.auxiliary.text.ExceptionText;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;

import java.util.UUID;

@UtilityClass
public final class UuidUtils {

    public static UUID fromString(String stringUuid) throws UuidUtilsException {
        try {
            return UUID.fromString(stringUuid);
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new UuidUtilsException(ExceptionText.UUID_UTILS_EXCEPTION.formatted(stringUuid), e);
        }
    }

}
