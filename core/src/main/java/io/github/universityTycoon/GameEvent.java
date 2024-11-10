package io.github.universityTycoon;

import java.util.ArrayList;

/**
 * Something that can happen during the course of the game. A pop-up is shown to the user which they can react to by
 * adding/removing buildings.
 */
public class GameEvent {
    String title;
    String description;
    float rarity;
    String iconPath; // E.g. "assets/icons/goose_event.png"
    ArrayList<GameModifiers> modifiers; // The effects the event applies, e.g. "Flooding"
}
