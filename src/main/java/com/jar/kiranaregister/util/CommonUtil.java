package com.jar.kiranaregister.util;

import java.text.DecimalFormat;

/**
 * Utility class providing common functionalities.
 */
public class CommonUtil {

    /**
     * Converts a double value to a two-decimal-place precision.
     *
     * @param originalValue The original double value to be converted.
     * @return The double value with two-decimal-place precision.
     */
    public static Double convertToTwoDecimalPlaces(Double originalValue) {
        // Use DecimalFormat to format the original value to two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(originalValue));
    }
}
