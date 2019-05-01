package com.group.game.utility;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by gerard on 09/11/2016.
 * Updated 17/02/18
 */

public class Constants {
    //Screen Size
    public static final float VIRTUAL_WIDTH = Gdx.graphics.getWidth();
    public static final float VIRTUAL_HEIGHT = Gdx.graphics.getHeight();

    //World to screen scale
    public static final float TILE_SIZE   = 32; // Needs to be changed to 64 once character sprite is in game
    public static final float UNITSCALE = 1.0f / TILE_SIZE;

    //Animation Speed
    public static final float FRAME_DURATION = 1.0f / 30.0f;
    public static final float TIME_STEP=1/60f;
    public static final int LEVEL_TIME = 30;

    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;

    public static final String BACKGROUND = "tileData/assignment_two_2.tmx";
    public static final String PHYSICS_MATERIALS_PATH = "tileData/physicsData.json";

    public static final float DENSITY=.5f;
    public static final float FRICTION=.5f;
    public static final float RESTITUTION=.5f;

    //impulse strength
    public static final float FORCE_X=30f;
    public static final float FORCE_Y=30f;

    //Speed
    public static final float MAX_VELOCITY = 1f;
    public static final float MAX_HEIGHT = 18;

    //player body
    public static int PLAYER_WIDTH= 3;
    public static int PLAYER_HEIGHT=4;
    public static float PLAYER_OFFSET_Y=2.15f;
    public static float PLAYER_OFFSET_X=1.5f;

    //player graphics
    public static final String PLAYER_ATLAS_PATH = "atlas/smurf_assets.atlas";
    public static final Texture MEDIUM = new Texture(Gdx.files.internal("gfx/mediumSize.png"));
    public static final Texture SMALL = new Texture(Gdx.files.internal("gfx/smallSize.png"));
    public static final Texture TINY = new Texture(Gdx.files.internal("gfx/tinySize.png"));

    //player start position
    public static final Vector2 START_POSITION = new Vector2(10,10);

    // Collision rectangle width and height
    public static final int COLLISION_RECT_WIDTH = 1;
    public static final int COLLISION_RECT_HEIGHT = 1;

    // Sprites
    public static final int MAX_BONUS_SPRITES = 10;
    public static final float MAX_TIME_TO_NEXT_BONUS = 5.0f;
    public static final float MIN_TIME_TO_NEXT_BONUS = 3.0f;
    public static final float MIN_POS_TO_NEXT_BONUS = 5.0f;
    public static final float MAX_POS_TO_NEXT_BONUS = 10.0f;
    public static final float CLAMP_HEIGHT = 5.0f;

    // Different sprites that will appear
    public static final String POWER_UP_BADGE_PATH = "texture_atlas/badge_assets.atlas";
    public static final String POWER_UP_GOOD_BOOST_PATH = "texture_atlas/boost2_assets.atlas";
    public static final String POWER_UP_PLAYER_PATH = "texture_atlas/player_assets.atlas";
    public static final String POWER_DOWN_BARREL_PATH = "texture_atlas/barrel_assets.atlas";
    public static final String POWER_DOWN_BAD_BOOST_PATH = "texture_atlas/boost1_assets.atlas";
    public static final String POWER_DOWN_ENEMY_PATH = "texture_atlas/enemy_assets.atlas";
    public static final String POWER_DOWN_ROCK_PATH = "texture_atlas/rock_assets.atlas";

    // PowerUp value
    public static final int POWERUP_VALUE = 50;
    public static final int POWERDOWN_VALUE = -50;
}
