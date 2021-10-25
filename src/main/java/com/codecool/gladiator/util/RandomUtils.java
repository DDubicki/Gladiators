package com.codecool.gladiator.util;

import java.util.Random;

public class RandomUtils {

    private static final Random RANDOM = new Random();

    public static int getRandomIntNumberFromRange(int minBound, int maxBound) {
        return RANDOM.nextInt((maxBound - minBound + 1)) + minBound;
    }

    public static int getRandomNumber(int bound) {
        return RANDOM.nextInt(bound);
    }

    public static double getRandomDoubleNumberFromRange(double minBound, double maxBound) {
        return RANDOM.nextDouble() * (maxBound - minBound) + minBound;
    }
}
