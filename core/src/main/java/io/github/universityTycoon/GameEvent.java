package io.github.universityTycoon;

import java.util.ArrayList;

public class GameEvent {
    String title;
    String description;
    float rarity;
    String iconPath; // E.g. "assets/icons/goose_event.png"
    ArrayList<GameModifiers> modifiers; // The effects the event applies, e.g. "Flooding"
}
