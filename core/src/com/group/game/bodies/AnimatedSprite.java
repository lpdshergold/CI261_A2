package com.group.game.bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import com.group.game.Sound.soundLink;

import java.util.Comparator;

import static com.group.game.utility.Constants.COLLISION_RECT_HEIGHT;
import static com.group.game.utility.Constants.COLLISION_RECT_WIDTH;
import static com.group.game.utility.Constants.FRAME_DURATION;
import static com.group.game.utility.Constants.PLAYER_HEIGHT;
import static com.group.game.utility.Constants.PLAYER_WIDTH;


/**
 * Created by gerard on 09/11/2016.
 * updated 02/03/18
 */

public abstract class AnimatedSprite extends Sprite {
    protected Animation animation;
    protected Animation.PlayMode playmode;
    private TextureAtlas atlas;
    private soundLink soundLink;

    // Collision data
    private Rectangle collisionRectangle;

    public AnimatedSprite(String atlasString, Texture t, Vector2 pos){
        super(t,PLAYER_WIDTH,PLAYER_HEIGHT);
        this.setX(pos.x);
        this.setY(pos.y);
        playmode = Animation.PlayMode.NORMAL;
        initAtlas(atlasString);
        // Instance of soundLink to call sounds in PowerUp/DownSprite
        soundLink = new soundLink();

        // Create collision rectangle in constructor
        createCollisionRect();

        // Set animations to be looping - called to stop in PowerDownSprite and PowerUpSprite
        Array<TextureAtlas.AtlasRegion> regions = new Array<TextureAtlas.AtlasRegion>(atlas.getRegions());
        regions.sort(new RegionComparator());
        animation = new Animation(FRAME_DURATION, regions, Animation.PlayMode.LOOP);
    }

    private void createCollisionRect() {
        this.collisionRectangle = new Rectangle(this.getX(), this.getY(), COLLISION_RECT_WIDTH, COLLISION_RECT_HEIGHT);
    }

    private void updateCollisionData() {
        collisionRectangle.setX(this.getX());
        collisionRectangle.setY(this.getY());
    }

    private Rectangle getCollisionRectangle() {return collisionRectangle;}

    public void update(float animationTime){
        this.setRegion((TextureRegion) animation.getKeyFrame(animationTime));

        // Keep collision rectangle in sync with physics body
        updateCollisionData();
    }

    private void initAtlas(String atlasString){
        atlas = new TextureAtlas(Gdx.files.internal(atlasString));
        //load animations
        Array<TextureAtlas.AtlasRegion> regions = new
                Array<TextureAtlas.AtlasRegion>(atlas.getRegions());
        regions.sort(new RegionComparator());
        animation = new Animation(FRAME_DURATION,regions, Animation.PlayMode.NORMAL);
    }

    private static class RegionComparator implements Comparator<TextureAtlas.AtlasRegion> {
        @Override
        public int compare(TextureAtlas.AtlasRegion region1, TextureAtlas.AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }

    // Set animation to be called from power sprite classes to change the animation type
    public void setAnimation(Animation.PlayMode pm) {
        animation.setPlayMode(pm);
    }


}