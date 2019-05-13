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

// import java.util.Arrays;      (part of the unused, random spawn method)
// import java.util.Collections; (part of the unused, random spawn method)

public class PowerUpSprite extends AnimatedSprite {
    private TweenData tweenData;
    private TweenManager tweenManager;
    private TweenCallback callback;
    public static boolean handlingCollision = true;
    public static boolean badgeCol = true;
    public static boolean boostCol = true;
    public static boolean playerCol = true;

    // private Float[] spawnVectorsX = {10.0f, 20.0f, 30.0f, 40.0f, 50.0f}; (part of the unused, random spawn method)
    // private Float[] spawnVectorsY = {5.0f, 7.5f, 10.0f, 12.5f, 15.0f};   "
    // public Vector2 randomSpawnVector;                                    "
    //                                                                      "
    // public Vector2 createRandomSpawnVector () {                          "
    //                                                                      "
    //     float vectorPartX;                                               "
    //     float vectorPartY;                                               "
    //                                                                      "
    //     Collections.shuffle(Arrays.asList(spawnVectorsX));               "
    //     Collections.shuffle(Arrays.asList(spawnVectorsY));               "
    //                                                                      "
    //     vectorPartX = spawnVectorsX[0];                                  "
    //     vectorPartY = spawnVectorsY[0];                                  "
    //                                                                      "
    //     randomSpawnVector.add(vectorPartX, vectorPartY);                 "
    //                                                                      "
    //     return randomSpawnVector;                                        "
    //                                                                      "
    // }                                                                    (part of the unused, random spawn method)

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

    // Called in GameScreen by PowerUpSprites to run the correct routine
    public void runningRoutines(String name) {
        if(name.equals("badgeDestroy")) {
            badgeDestroyRoutine();
        } else if(name.equals("boostDestroy")) {
            boostDestroyRoutine();
        } else if (name.equals("playerDestroy")) {
            playerDestroyRoutine();
        } else if (name.equals("shrink")) {
            shrinkSprite();
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

    public void shrinkSprite() {
        Timeline.createSequence()
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_SCALE, 0f)
                    .target(getScaleX() - 0.3f, getScaleY() - 0.3f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {

                    }
                })
                .start(tweenManager);
    }

    // Destroy routine for badge sprite
    public void badgeDestroyRoutine() {
        soundLink.play(2);
        Timeline.createSequence()
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_POS, 30f)
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

    // Destroy routine for boost sprite
    public void boostDestroyRoutine() {
        Timeline.createSequence()
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_ROTATION, 25f)
                    .target(getRotation() - 25))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_ROTATION, 25f)
                        .target(getRotation() + 25))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_ROTATION, 25f)
                        .target(getRotation() - 25))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_POS, 25f)
                    .target(getX(), getY() + 10))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {

                    }
                })
        .start(tweenManager);
    }

    // Destroy routine for player sprite
    public void playerDestroyRoutine() {
        soundLink.play(6);
        Timeline.createSequence()
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_ROTATION, 75f)
                    .target(getRotation() + 360))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_SCALE, 50f)
                    .target(getScaleX() - 0.7f, getScaleY() - 0.7f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {

                    }
                })
        .start(tweenManager);
    }
}

