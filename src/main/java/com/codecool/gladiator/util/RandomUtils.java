package com.codecool.gladiator.util;

import java.util.Random;

public class RandomUtils {

    private static final Random RANDOM = new Random();

    public static int getRandomNumberInBound(int minBound, int maxBound) {
        return RANDOM.nextInt((maxBound - minBound + 1)) + minBound;
    }
}
