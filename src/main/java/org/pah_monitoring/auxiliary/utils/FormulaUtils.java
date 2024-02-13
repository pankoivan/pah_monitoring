package org.pah_monitoring.auxiliary.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class FormulaUtils {

    public static double bodyMassIndex(double weight, int height) {
        return weight / (height * height) / 10000;
    }

}
