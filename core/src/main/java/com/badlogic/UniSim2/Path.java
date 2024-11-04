package com.badlogic.UniSim2;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A class which represents a path from one path to another on the {@link Map}.
 */
public class Path extends Sprite{
    
    /**
     * Creates a new path at the specified (x, y) coord with the specified width
     * and height and adds it to to {@link Map#collidableSprites}.
     * @param x x coord of the bottom left corner of the path.
     * @param y y coord of the bottom left corner of the path.
     * @param width
     * @param height
     */
    public Path(int x, int y, int width, int height){
        setPosition(x, y);
        setSize(width, height);
        Map.collidableSprites.add(this);
    }
    
}
