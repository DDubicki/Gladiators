package com.codecool.gladiator.view;

import com.codecool.gladiator.util.RandomUtils;

/**
 * Basic console view implementation
 */
public class ConsoleView implements Viewable {

    @Override
    public void display(String text) {
        System.out.println(text);
    }

    @Override
    public int getNumberBetween(int min, int max) {
        return RandomUtils.getRandomIntNumberFromRange(min, max);
    }

}
