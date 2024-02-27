package org.pah_monitoring.auxiliary.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class NumberUtils {

    public static double round(double number, int precision) {
        return Double.parseDouble(String.format("%%.%sf".formatted(precision), number).replaceAll(",", "."));
    }

}
