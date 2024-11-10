package io.github.universityTycoon;

import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;

/**
 * Placeholder class to select tracks based on the game-state.
 */
public class AudioSelector {
    ArrayList<Music> tracks;

    public AudioSelector() {
        tracks = new ArrayList<>();
        // Put music here
    }

    public Music selectTrack(GameModel gameModel) {
        return tracks.get(0);
    }
}
