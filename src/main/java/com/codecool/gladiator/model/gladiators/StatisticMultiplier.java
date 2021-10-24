package com.codecool.gladiator.model.gladiators;

public enum StatisticMultiplier {
    LOW(0.75),
    MEDIUM(1.0),
    HIGH(1.25);

    public final double value;

    StatisticMultiplier(double value) {
        this.value = value;
    }
}
