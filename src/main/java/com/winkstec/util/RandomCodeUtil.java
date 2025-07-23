package com.winkstec.util;

import java.util.Random;

public class RandomCodeUtil {
    public static String generate6DigitCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
