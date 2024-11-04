package com.badlogic.UniSim2;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Path extends Sprite{
    
    public Path(int x, int y, int width, int height){
        setPosition(x, y);
        setSize(width, height);
        Map.collidableSprites.add(this);
    }
    
}
