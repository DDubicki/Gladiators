package com.codecool.gladiator.model.gladiators;

public abstract class Gladiator {

    private final String name;
    private final int baseHp;
    private final int baseSp;
    private final int baseDex;
    private int level;
    private int hp;

    /**
     * Constructor for Gladiators
     *
     * @param name    the gladiator's name
     * @param baseHp  the gladiator's base Health Points
     * @param baseSp  the gladiator's base Strength Points
     * @param baseDex the gladiator's base Dexterity Points
     * @param level   the gladiator's starting Level
     */
    public Gladiator(String name, int baseHp, int baseSp, int baseDex, int level) {
        this.name = name;
        this.baseHp = baseHp;
        this.baseSp = baseSp;
        this.baseDex = baseDex;
        this.level = level;
    }

    /**
     * @return HP multiplier of the gladiator subclass
     */
    protected abstract Multiplier getHpMultiplier();

    /**
     * @return SP multiplier of the gladiator subclass
     */
    protected abstract Multiplier getSpMultiplier();

    /**
     * @return DEX multiplier of the gladiator subclass
     */
    protected abstract Multiplier getDexMultiplier();

    /**
     * @return Gladiator's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the full name of the gladiator
     * assembled by the subtype and the name
     * (e.g. "Brutal Brutus" or "Archer Leo")
     *
     * @return the full name
     */
    public String getFullName() {
        return getClass().getSimpleName() + " " + name;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        this.level++;
    }

    public int getCurrentHp() {
        return hp;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void decreaseHpBy(int value) {
        hp -= value;
    }

    public void healUp() {
        hp = getMaximumHp();
    }

    public final int getMaximumHp() {
        return (int) (baseHp * getHpMultiplier().value * level);
    }

    public int getSp() {
        return baseSp;
    }

    public int getDex() {
        return baseDex;
    }

    public int getHp() {
        return hp;
    }

    public final int getMaximumSp() {
        return (int) (baseSp * getSpMultiplier().value * level);
    }

    public final int getMaximumDex() {
        return (int) (baseDex * getDexMultiplier().value * level);
    }

    public enum Multiplier {
        Low(0.75),
        Medium(1.0),
        High(1.25);

        private final double value;

        Multiplier(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

    }
}
