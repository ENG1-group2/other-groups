package io.github.universityTycoon;

import java.util.ArrayList;

public class EventManager {
    ArrayList<GameEvent> eventList = new ArrayList<GameEvent>();
    GameEventListener listener;

    public EventManager(GameEventListener listener) {
        this.listener = listener;
    }

    // Run this inside main process loop
    public void ProcessEvents(float delta) {
        if (false) { // Calculate whether to raise an event
            listener.raiseEvent(eventList.get(0));
        }
    }
}
