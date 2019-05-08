package com.group.game.bodies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.group.game.utility.TweenData;
import com.group.game.utility.TweenDataAccessor;
import com.group.game.utility.UniversalResource;
import com.group.game.Sound.soundLink;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class PowerUpSprite extends AnimatedSprite {
    private TweenData tweenData;
    private TweenManager tweenManager;
    private TweenCallback callback;
    public static boolean handlingCollision = true;
    public static boolean badgeCol = true;
    public static boolean boostCol = true;
    public static boolean playerCol = true;


    public PowerUpSprite(String atlasString, Texture t, Vector2 pos) {
        super(atlasString, t, pos);
        // Alpha set a 0 means the sprite cannot be seen
        this.setAlpha(1);
        this.setPosition(pos.x, pos.y);
        callback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                initTweenData();
                handlingCollision = true;
            }
        };
        initTweenData();
    }

    private TweenData getTweenData()
    {
        return tweenData;
    }

    private void initTweenData() {
        tweenData = new TweenData();
        tweenData.setXy(new Vector2 (this.getX(), this.getY()));
        tweenData.setColor(this.getColor());
        tweenData.setScale(this.getScaleX());
        tweenManager = UniversalResource.getInstance().tweenManager; //tweenManager
    }

    public void runningRoutines(String name) {
        if(name.equals("badgeDestroy")) {
            badgeDestroyRoutine();
        }
    }

    @Override
    public void update(float stateTime) {
        super.update(stateTime);
        this.setX(tweenData.getXy().x);
        this.setY(tweenData.getXy().y);
        this.setColor(tweenData.getColor());
        this.setScale(tweenData.getScale());
        this.setRotation(tweenData.getRotation());
    }

    public void badgeDestroyRoutine() {
        soundLink.play(1);
        Timeline.createSequence()
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_POS, 20f)
                    .target(getX(), getY() + 2f))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_ROTATION, 10f)
                    .target(getRotation() - 25))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_POS, 30f)
                .target(getX(), getY() - 15f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                    }
                })
                .start(tweenManager);
    }

    public void destroyRoutine() {

    }
}

