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
    private final boolean firstGladiatorIsAttacker = RandomUtils.getRandomBoolean();
    private boolean someoneIsDead;

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
        do {
            makeTurn();
            someoneIsDead = (gladiator1.isDead() || gladiator2.isDead());
        } while (!someoneIsDead);
        return pickWinner();
    }

    private Gladiator pickWinner() {
        Gladiator winner = getWinner();
        Gladiator loser = getSecondGladiator(winner);
        String winLog = String.format("%s has died, %s wins!", loser.getFullName(), winner.getFullName());
        combatLog.add(winLog);
        return winner;
    }

    private Gladiator getSecondGladiator(Gladiator firstGladiator) {
        return firstGladiator == gladiator1 ? gladiator2 : gladiator1;
    }

    private void makeTurn() {
        Gladiator actualAttacker = firstGladiatorIsAttacker ? gladiator1 : gladiator2;
        Gladiator actualDefender = getSecondGladiator(actualAttacker);


        int hittingChance = getHittingChance(actualAttacker, actualDefender);
        boolean isHitting = RandomUtils.isLuckyHit(hittingChance);
        if (isHitting) {
            decreaseDefendersHp(actualAttacker, actualDefender);
        } else {
            String missLog = String.format("%s missed", actualAttacker.getFullName());
            combatLog.add(missLog);
        }
    }

    private void decreaseDefendersHp(Gladiator actualAttacker, Gladiator actualDefender) {
        double randomDouble = RandomUtils.getRandomDoubleNumberFromRange(MIN_DAMAGE_NUMBER, MAX_DAMAGE_NUMBER);
        int damage = (int) (actualAttacker.getMaximumSp() * randomDouble);
        actualDefender.decreaseHpBy(damage);
        String hitLog = String.format("%s deals %s damage", actualAttacker.getFullName(), damage);
        combatLog.add(hitLog);
    }

    private int getHittingChance(Gladiator actualAttacker, Gladiator actualDefender) {
        int attackerDex = actualAttacker.getMaximumDex();
        int defenderDex = actualDefender.getMaximumDex();
        int dexDifference = attackerDex - defenderDex;
        int percentageChanceToHitting = Math.max(10, dexDifference);
        return Math.min(100, percentageChanceToHitting);
    }

    private Gladiator getWinner() {
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
