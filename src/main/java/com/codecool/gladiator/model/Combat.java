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
        if (gladiator1 == null)
            return null;
        if (gladiator2 == null)
            return null;

        Gladiator actualAttacker = RandomUtils.getRandomGladiator(gladiator1, gladiator2);
        Gladiator actualDefender = actualAttacker == gladiator1 ? gladiator2 : gladiator1;

        makeTurn(actualAttacker, actualDefender);

        // Check this
        return checkWinner();
    }


    private void makeTurn(Gladiator actualAttacker, Gladiator actualDefender) {
        int hittingChance = getHittingChance(actualAttacker, actualDefender);
        boolean isHitting = RandomUtils.isLuckyHit(hittingChance);
        if (isHitting) {
            double randomDouble = RandomUtils.getRandomDoubleNumberFromRange(MIN_DAMAGE_NUMBER, MAX_DAMAGE_NUMBER);
            int damage = (int) (actualAttacker.getMaximumSp() * randomDouble);
            actualDefender.decreaseHpBy(damage);
        }
    }

    private int getHittingChance(Gladiator actualAttacker, Gladiator actualDefender) {
        int attackerDex = actualAttacker.getMaximumDex();
        int defenderDex = actualDefender.getMaximumDex();
        int dexDifference = attackerDex - defenderDex;
        int percentageChanceToHitting = Math.max(10, dexDifference);
        return Math.min(100, percentageChanceToHitting);
    }

    private Gladiator checkWinner() {
        return gladiator1.getCurrentHp() <= 0 ? gladiator2 : gladiator1;
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
