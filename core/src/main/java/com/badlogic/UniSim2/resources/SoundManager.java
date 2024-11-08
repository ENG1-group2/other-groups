package com.badlogic.UniSim2.resources;

public class SoundManager {

    private SoundManager(){};

    public static void playMusic(){
        Assets.music.setVolume(.5f);
        Assets.music.setLooping(true);
        Assets.music.play();
    }

    public static void stopMusic(){
        Assets.music.stop();
    }

    public static void playClick(){
        Assets.click.play();
    }
}
