package com.codecool.gladiator.model;

import com.codecool.gladiator.model.gladiators.Gladiator;
import com.codecool.gladiator.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Combat class, used for simulating fights between pairs of gladiators
 */
public class Combat {

    private static final double MIN_DAMAGE_NUMBER = 0.1;
    private static final double MAX_DAMAGE_NUMBER = 0.5;
    private final Gladiator gladiator1;
    private final Gladiator gladiator2;

    private final List<String> combatLog;

    public Combat(Contestants contestants) {
        this.gladiator1 = contestants.gladiator1;
        this.gladiator2 = contestants.gladiator2;
        this.combatLog = new ArrayList<>();
    }

    /**
     * Simulates the combat and returns the winner.
     * If one of the opponents is null, the winner is the one that is not null
     * If both of the opponents are null, the return value is null
     *
     * @return winner of combat
     */
    public Gladiator simulate() {
        Gladiator firstAttacker = getFirstAttackerGladiator();
        Gladiator secondAttacker = firstAttacker == gladiator1 ? gladiator2 : gladiator1;

        makeTurn(firstAttacker, secondAttacker);


        return checkWinner();
    }

    private void makeTurn(Gladiator firstAttacker, Gladiator secondAttacker) {
        int hittingChance = getHittingChance(firstAttacker, secondAttacker);
        double R = RandomUtils.getRandomDoubleNumberFromRange(MIN_DAMAGE_NUMBER, MAX_DAMAGE_NUMBER);
        int damage = (int) (firstAttacker.getMaximumSp() * R);
        int i = secondAttacker.getCurrentHp() - damage;
    }

    private int getHittingChance(Gladiator firstAttacker, Gladiator secondAttacker) {
        int attackerDex = firstAttacker.getMaximumDex();
        int defenderDex = secondAttacker.getMaximumDex();
        int dexDifference = attackerDex - defenderDex;
        int percentageChanceToHitting = Math.max(10, dexDifference);
        return Math.min(100, percentageChanceToHitting) / 100;
    }

    private Gladiator getFirstAttackerGladiator() {
        int randomNumber = RandomUtils.getRandomIntNumberFromRange(1, 3);
        if (randomNumber == 2)
            return gladiator2;
        return gladiator1;
    }

    private Gladiator checkWinner() {
        if (gladiator1 == null && gladiator2 == null) {
            return null;
        } else {
            if ((gladiator1 != null ? gladiator1.getMaximumHp() : 0) <= 0) {
                return gladiator2;
            }
            return gladiator1;
        }
    }

    public Gladiator getGladiator1() {
        return gladiator1;
    }

    public Gladiator getGladiator2() {
        return gladiator2;
    }

    public String getCombatLog(String separator) {
        return String.join(separator, combatLog);
    }

}
