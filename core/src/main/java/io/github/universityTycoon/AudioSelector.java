package io.github.universityTycoon;

import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;

public class AudioSelector {
    ArrayList<Music> tracks;

    public AudioSelector() {
        tracks = new ArrayList<>();
        // Put music here
    }

    public Music selectTrack() {
        return tracks.get(0);
    }
}
