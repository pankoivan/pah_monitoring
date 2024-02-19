package org.pah_monitoring.auxiliary.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class FormulaUtils {

    public static double bodyMassIndex(double weight, int height) {
        return weight / ((double) (height * height) / 10000);
    }

    public static double tlc(double rlc, double vlc) {
        return rlc + vlc;
    }

    public static double tiffnoIndex(double vfe1, double vlc) {
        return vfe1 / vlc;
    }

}
