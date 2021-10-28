package com.codecool.gladiator.controller;

import com.codecool.gladiator.model.Combat;
import com.codecool.gladiator.model.Contestants;
import com.codecool.gladiator.model.gladiators.*;
import com.codecool.gladiator.util.Tournament;
import com.codecool.gladiator.view.Viewable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Colosseum {

    public static final int MIN_TOURNAMENT_STAGES = 1;
    public static final int MAX_TOURNAMENT_STAGES = 10;

    private final Viewable view;
    private final GladiatorFactory gladiatorFactory;
    private int stages;

    public Colosseum(Viewable view, GladiatorFactory gladiatorFactory) {
        this.view = view;
        this.gladiatorFactory = gladiatorFactory;
    }

    /**
     * Runs the Tournament simulation
     */
    public void runSimulation() {
        var numberOfGladiators = (int) Math.pow(2, stages);
        var gladiators = generateGladiators(numberOfGladiators);
        var contestants = splitGladiatorsIntoPairs(gladiators);
        var tournamentTree = new Tournament(contestants);
        var champion = getChampion(tournamentTree);
        announceChampion(champion);

        // The following line chains the above lines:
//         announceChampion(getChampion(new BinaryTree<>(generateGladiators((int) Math.pow(2, stages)))));
    }

    private List<Gladiator> generateGladiators(int numberOfGladiators) {
        List<Gladiator> gladiators = new ArrayList<>();
        GladiatorFactory factory = new GladiatorFactory("Names.txt");
        for (int i = 0; i < numberOfGladiators; i++) {
            Gladiator gladiator = factory.generateRandomGladiator();
            gladiators.add(gladiator);
        }
        introduceGladiators(gladiators);
        return gladiators;
    }

    private List<Contestants> splitGladiatorsIntoPairs(List<Gladiator> gladiators) {
        LinkedList<Contestants> gladiatorsPairs = new LinkedList<>();
        for (int i = 0; i < gladiators.size(); i = i + 2) {
            Gladiator gladiator1 = gladiators.get(i);
            Gladiator gladiator2 = gladiators.get(i + 1);
            Contestants contestants = new Contestants(gladiator1, gladiator2);
            gladiatorsPairs.add(contestants);
        }
        return gladiatorsPairs;
    }

    private Gladiator getChampion(Tournament tournament) {
        int battlesNumbers = tournament.size();
        for (int i = 0; i < battlesNumbers; i++) {
            Tournament startBrunch = tournament.getLeftBranch();
            takeCurrentBranch(startBrunch);
        }
        return null;
    }

    private Tournament takeCurrentBranch(Tournament currentBranch) {
        Tournament newBranch = currentBranch;
        if (currentBranch.getLeftBranch() != null) {
            newBranch = currentBranch.getLeftBranch();
            takeCurrentBranch(newBranch);
        } else if (currentBranch.getRightBranch() != null) {
            newBranch = currentBranch.getRightBranch();
            takeCurrentBranch(newBranch);
        }
        Contestants contestants = newBranch.getContestants();
        Combat combat = new Combat(contestants);
        simulateCombat(combat);
        return currentBranch;
    }

    private Gladiator simulateCombat(Combat combat) {
        Gladiator gladiator1 = combat.getGladiator1();
        Gladiator gladiator2 = combat.getGladiator2();
        announceCombat(gladiator1, gladiator2);

        // Todo
        combat.simulate();

        displayCombatLog(combat);
        announceWinnerAndLoser(gladiator1, gladiator2);
        return gladiator1;
    }

    public void welcome() {
        view.display("\nAve Caesar, and welcome to the Colosseum!");
    }

    public void welcomeAndAskForStages() {
        Scanner scanner = new Scanner(System.in);
        welcome();
        do {
            view.display("How many stages of the Tournament do you wish to watch? (1-10)");
            stages = scanner.nextInt();
        } while (stages < MIN_TOURNAMENT_STAGES || stages > MAX_TOURNAMENT_STAGES);
    }

    private void introduceGladiators(List<Gladiator> gladiators) {
        view.display(String.format("\nWe have selected Rome's %d finest warriors for today's Tournament!", gladiators.size()));
        for (Gladiator gladiator : gladiators) {
            view.display(String.format(" - %s", gladiator.getFullName()));
        }
        view.display("\n\"Ave Imperator, morituri te salutant!\"");
    }

    private void announceCombat(Gladiator gladiator1, Gladiator gladiator2) {
        view.display(String.format("\nDuel %s versus %s:", gladiator1.getName(), gladiator2.getName()));
        view.display(getGladiatorsData(gladiator1));
        view.display(getGladiatorsData(gladiator2));
    }

    private String getGladiatorsData(Gladiator gladiator) {
        String name = String.format(" - %s", gladiator.getFullName());
        String characteristics = String.format(" (%s/%s HP, %s SP, %s DEX, %s LVL)",
                gladiator.getMaximumHp(),
                gladiator.getMaximumHp(),
                gladiator.getMaximumSp(),
                gladiator.getMaximumDex(),
                gladiator.getLevel());
        return name + characteristics;
    }

    private void displayCombatLog(Combat combat) {
        view.display(String.format(" - %s", combat.getCombatLog(", ")));
    }

    private void announceWinnerAndLoser(Gladiator winner, Gladiator loser) {
        view.display(String.format("%s has died, %s wins!", loser.getFullName(), winner.getFullName()));
    }

    private void announceChampion(Gladiator champion) {
        if (champion != null) {
            view.display(String.format("\nThe Champion of the Tournament is %s!", champion.getFullName()));
        } else {
            view.display("\nHave mercy, Caesar, the Tournament will start soon!");
        }
    }

}
