package io.github.universityTycoon;

import java.util.ArrayList;

public class EventManager {
    //float eventChancePerSec; Removed due to "rarity" inside Event class
    ArrayList<GameEvent> eventList = new ArrayList<GameEvent>();
    GameEventListener listener;

    public EventManager(GameEventListener listener) {
        this.listener = listener;
    }

    // Run this inside main process loop
    public void ProcessEvents(float currentTime) { // Time might not be a float idk
        if (false) { // Calculate whether to raise an event
            listener.raiseEvent(eventList.get(0));
        }
    }
}
