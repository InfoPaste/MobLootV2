package me.InfoPaste.MobLoot.utils;

import java.util.Random;

public class NumUtil {

    public static Double getRandomValue(final double lowerBound, final double upperBound, final int decimalPlaces) {

        final Random random = new Random();

        if (lowerBound < 0 || upperBound <= lowerBound || decimalPlaces < 0) {
            // Error
            return -1.0;
        }

        final double dbl = ((random == null ? new Random() : random).nextDouble() * (upperBound - lowerBound)) + lowerBound;

        return Double.valueOf(String.format("%." + decimalPlaces + "f", dbl).replaceAll("[^\\d.]", ""));

    }
}
