package com.opham.prepa.Utils;

import org.springframework.util.StringUtils;

import java.math.BigInteger;

public class Convert {
    public static String decimalToHexadecimal(int decimalNumber) {
        BigInteger bigInteger = BigInteger.valueOf(decimalNumber);
        String hexadecimalString = bigInteger.toString(16);
        return StringUtils.capitalize(hexadecimalString.toUpperCase()); // Optional: Capitalize the hexadecimal string
    }
}
