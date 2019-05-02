package com.group.game.bodies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.group.game.utility.TweenData;
import com.group.game.utility.UniversalResource;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class PowerUpSprite extends AnimatedSprite {
    private TweenData tweenData;
    private TweenManager tweenManager;
    private TweenCallback callback;
    private float animationTime;
    private int ttl;
    private float timeCount;
    public static boolean handlingCollision = false;

    public PowerUpSprite(String atlasString, Texture t, Vector2 pos) {
        super(atlasString, t, pos);
        // Alpha set a 0 means the sprite cannot be seen
        this.setAlpha(1);
        callback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                initTweenData();
                handlingCollision = false;
            }
        };
        initTweenData();
    }



    private void initTweenData() {
        tweenData = new TweenData();
        tweenData.setXy(new Vector2 (this.getX(), this.getY()));
        tweenData.setColor(this.getColor());
        tweenData.setScale(this.getScaleX());
        tweenManager = UniversalResource.getInstance().tweenManager; //tweenManager
    }

    public void startRoutine() {
        // Tween routines are used to create animations once something has been collided with - i.e. jumps slightly in the air, spins and falls off the screen
        // Like Goombas in Super Mario when they are killed
    }

    public void destroyRoutine() {

    }
}

