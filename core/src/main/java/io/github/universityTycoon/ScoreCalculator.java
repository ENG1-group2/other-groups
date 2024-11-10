package io.github.universityTycoon;

import java.util.ArrayList;

/**
 * Class to invoke to calculate the current satisfaction score. Stores a list of active modifiers that can affect how
 * satisfaction is calculated.
 */
public class ScoreCalculator {
    ArrayList<GameModifiers> activeModifiers = new ArrayList<GameModifiers>();
    int currentScore; // Possibly unnecessary
    int currentStudents;

    // This will probably need a lot of parameters
    public int calculateScore() {
        return 0;
    }
}
