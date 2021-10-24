package com.codecool.gladiator.model.gladiators;

import com.codecool.gladiator.util.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class GladiatorFactory {

    int MIN_RANGE_VALUE = 25;
    int MAX_RANGE_VALUE = 100;
    int LEVEL_MIN_RANGE_VALUE = 1;
    int LEVEL_MAX_RANGE_VALUE = 5;
    private List<String> names;

    public GladiatorFactory(String fileOfNames) {
        try {
            File file = new File(getClass().getClassLoader().getResource(fileOfNames).getFile());
            names = Files.readAllLines(file.toPath());
        } catch (IOException|NullPointerException e) {
            System.out.println("Names file not found or corrupted!");
            System.exit(1);
        }
    }

    /**
     * Picks a random name from the file given in the constructor
     *
     * @return gladiator name
     */
    private String getRandomName() {
        return names.get(RandomUtils.getRandomNumber(names.size()));
    }

    /**
     * Instantiates a new gladiator with random name and type.
     * Creating an Archer, an Assassin, or a Brutal has the same chance,
     * while the chance of creating a Swordsman is the double of the chance of creating an Archer.
     * @return new Gladiator
     */
    public Gladiator generateRandomGladiator() {
        int BASE_HP = RandomUtils.getRandomNumberInRange(MIN_RANGE_VALUE, MAX_RANGE_VALUE);
        int BASE_SP = RandomUtils.getRandomNumberInRange(MIN_RANGE_VALUE, MAX_RANGE_VALUE);
        int BASE_DEX = RandomUtils.getRandomNumberInRange(MIN_RANGE_VALUE, MAX_RANGE_VALUE);
        int START_LEVEL = RandomUtils.getRandomNumberInRange(LEVEL_MIN_RANGE_VALUE, LEVEL_MAX_RANGE_VALUE);

        int randomNumber = RandomUtils.getRandomNumber(5);
        switch (randomNumber){
            case 0:
                return new Archer(getRandomName(), BASE_HP, BASE_SP, BASE_DEX, START_LEVEL);
            case 1:
                return new Brutal(getRandomName(), BASE_HP, BASE_SP, BASE_DEX, START_LEVEL);
            case 2:
                return new Assassin(getRandomName(), BASE_HP, BASE_SP, BASE_DEX, START_LEVEL);
            case 3:
            case 4:
                return new Swordsman(getRandomName(), BASE_HP, BASE_SP, BASE_DEX, START_LEVEL);
        }
        throw new IllegalStateException("There are no such Gladiator type with this number: " + randomNumber);
    }
}
