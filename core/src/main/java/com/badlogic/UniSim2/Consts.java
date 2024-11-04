package com.badlogic.UniSim2;

import com.badlogic.gdx.graphics.Color;

/**
 * This class is used to manage all the constants in the game. It allows
 * easy changes to be made to all constants within it.
 */
public class Consts {

    public Consts(){}

    /**
     * Size of each cell in pixels, everything scales to the cell size.
     */
    public static final int CELL_SIZE = 20;

    /**
     * Scales a value according to the size of each grid cell.
     * @param value The value to scale.
     * @return value * {@link #CELL_SIZE}.
     */
    private static int scaleToCellSize(int value){
        return value * CELL_SIZE;
    }
    public static final int WORLD_WIDTH = scaleToCellSize(75);
    public static final int WORLD_HEIGHT = scaleToCellSize(38);

    public static final int START_BUTTON_WIDTH = scaleToCellSize(10);
    public static final int START_BUTTON_HEIGHT = scaleToCellSize(6);
    public static final int START_BUTTON_X = WORLD_WIDTH / 2 - START_BUTTON_WIDTH / 2;
    public static final int START_BUTTON_Y = WORLD_HEIGHT / 2 - START_BUTTON_HEIGHT / 2 - scaleToCellSize(5);

    public static final Color BACKGROUND_COLOR = new Color(Color.BLACK);

    public static final int PATH_SIZE = scaleToCellSize(2);

    public static final int H_PATH_1_X = scaleToCellSize(9);
    public static final int H_PATH_1_Y = WORLD_HEIGHT - scaleToCellSize(12);
    public static final int H_PATH_1_WIDTH = WORLD_WIDTH - H_PATH_1_X;

    public static final int H_PATH_2_X = scaleToCellSize(0);
    public static final int H_PATH_2_Y = WORLD_HEIGHT - scaleToCellSize(24);
    public static final int H_PATH_2_WIDTH = WORLD_WIDTH;

    public static final int V_PATH_1_X = scaleToCellSize(26);
    public static final int V_PATH_1_Y = scaleToCellSize(0);
    public static final int V_PATH_1_HEIGHT = WORLD_HEIGHT - scaleToCellSize(2);

    public static final int V_PATH_2_X = scaleToCellSize(46);
    public static final int V_PATH_2_Y = scaleToCellSize(0);
    public static final int V_PATH_2_HEIGHT = WORLD_HEIGHT - scaleToCellSize(10);

    public static final int V_PATH_3_X = scaleToCellSize(60);
    public static final int V_PATH_3_Y = scaleToCellSize(0);
    public static final int V_PATH_3_HEIGHT = WORLD_HEIGHT;

    public static final int V_PATH_4_X = scaleToCellSize(9);
    public static final int V_PATH_4_Y = scaleToCellSize(0);
    public static final int V_PATH_4_HEIGHT = WORLD_HEIGHT;

    public static final Color GRID_COLOR = new Color(0.28f,0.56f,0.11f, 1);
    public static final int GRID_ROWS = WORLD_HEIGHT / CELL_SIZE;
    public static final int GRID_COLS = WORLD_WIDTH / CELL_SIZE;

    public static final int MENU_BAR_X = scaleToCellSize(0);
    public static final int MENU_BAR_Y = scaleToCellSize(0);
    public static final int MENU_BAR_WIDTH = scaleToCellSize(9);
    public static final int MENU_BAR_HEIGHT = WORLD_HEIGHT;

    public static final int BUILDING_BUTTON_WIDTH = scaleToCellSize(7);
    public static final int BUILDING_BUTTON_HEIGHT = scaleToCellSize(3);
    public static final int BUILDING_BUTTON_X_BOUNDARY = scaleToCellSize(1) / 2;
    public static final int BUILDING_BUTTON_Y_BOUNDARY = WORLD_HEIGHT - scaleToCellSize(2);
    public static final int BUILDING_BUTTON_GAP = scaleToCellSize(2) + BUILDING_BUTTON_HEIGHT;

    public static final int TIMER_WIDTH = scaleToCellSize(6);
    public static final int TIMER_HEIGHT = scaleToCellSize(2);
    public static final int TIMER_X = scaleToCellSize(4);
    public static final int TIMER_Y = WORLD_HEIGHT - scaleToCellSize(2);
    public static final int TIMER_SIZE = 3;
    public static final Color TIMER_COLOR = new Color(Color.BLACK);
    public static final int MAX_TIME = 300;

    public static final Color COUNT_COLOR = new Color(Color.BLACK);
    public static final int COUNT_SIZE = 2;

    public static final int MAP_MIN_X_BOUNDARY = MENU_BAR_X + MENU_BAR_WIDTH;
    public static final int MAP_MAX_X_BOUNDARY = WORLD_WIDTH;
    public static final int MAP_MIN_Y_BOUNDARY = scaleToCellSize(0);
    public static final int MAP_MAX_Y_BOUNDARY = WORLD_HEIGHT;;

    public static final int ACCOMODATION_WIDTH = scaleToCellSize(4);
    public static final int ACCOMODATION_HEIGHT = scaleToCellSize(6);

    public static final int LECTUREHALL_WIDTH = scaleToCellSize(6);
    public static final int LECTUREHALL_HEIGHT = scaleToCellSize(10);

    public static final int LIBRARY_WIDTH = scaleToCellSize(10);
    public static final int LIBRARY_HEIGHT = scaleToCellSize(3);

    public static final int COURSE_WIDTH = scaleToCellSize(5);
    public static final int COURSE_HEIGHT = scaleToCellSize(5);

    public static final int FOODZONE_WIDTH = scaleToCellSize(2);
    public static final int FOODZONE_HEIGHT = scaleToCellSize(2);

    public static final int RECREATIONAL_WIDTH = scaleToCellSize(3);
    public static final int RECREATIONAL_HEIGHT = scaleToCellSize(4);

    public static final int NATURE_WIDTH = scaleToCellSize(4);
    public static final int NATURE_HEIGHT = scaleToCellSize(5);
}
