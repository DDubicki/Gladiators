package com.codecool.gladiator.util;

import com.codecool.gladiator.model.gladiators.Gladiator;

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

    public static Gladiator getRandomGladiator(Gladiator firstGladiator, Gladiator secondGladiator) {
        int randomNumber = RANDOM.nextInt(2);
        if (randomNumber == 0)
            return firstGladiator;
        return secondGladiator;
    }

    public static boolean isLuckyHit(int hittingChance) {
        return hittingChance == 100;
    }
}
