package com.group.game.bodies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.group.game.utility.TweenData;
import com.group.game.utility.TweenDataAccessor;
import com.group.game.utility.UniversalResource;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import com.group.game.Sound.soundLink;

public class PowerDownSprite extends AnimatedSprite {
    private TweenData tweenData;
    private TweenManager tweenManager;
    private TweenCallback callback;
    public static boolean handlingCollision = true;
    public static boolean barrelCol = true;
    public static boolean badBoostCol = true;
    public static boolean enemyCol = true;
    public static boolean rockCol = true;

    public PowerDownSprite(String atlasString, Texture t, Vector2 pos) {
        super(atlasString, t, pos);
        // Alpha set a 0 means the sprite cannot be seen
        this.setAlpha(1);
        callback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                initTweenData();
                handlingCollision = true;
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

    // Called in GameScreen by PowerDownSprites to run the correct routine
    public void runningRoutines(String name) {
        if(name.equals("barrelDestroy")) {
            barrelDestroyRoutine();
        } else if(name.equals("badBoostDestroy")) {
            badBoostDestroyRoutine();
        } else if (name.equals("enemyDestroy")) {
            enemyDestroyRoutine();
        } else if(name.equals("rockDestroy")) {
            rockDestroyRoutine();
        } else if(name.equals("shrink")) {
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

    public void barrelDestroyRoutine() {
        Timeline.createSequence()
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_ROTATION, 30f)
                    .target(getRotation() - 360))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_SCALE, 40f)
                    .target(getScaleX() - 0.7f, getScaleY() - 0.7f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        setAnimation(Animation.PlayMode.NORMAL);
                    }
                })
        .start(tweenManager);
    }

    public void badBoostDestroyRoutine() {
        Timeline.createSequence()
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_POS, 10f)
                        .target(getX() + 1, getY()))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_POS, 10f)
                        .target(getX() - 1, getY()))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_POS, 10f)
                        .target(getX() + 1, getY()))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_POS, 10f)
                        .target(getX() - 1, getY()))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_POS, 50f)
                        .target(getX(), getY() - 25))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        setAnimation(Animation.PlayMode.NORMAL);
                    }
                })
                .start(tweenManager);
    }

    public void enemyDestroyRoutine() {
        soundLink.play(6);
        Timeline.createSequence()
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_ROTATION, 50f)
                        .target(getRotation() -359))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_SCALE, 50f)
                        .target(getScaleX() -0.7f, getScaleY() - 0.7f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        setAnimation(Animation.PlayMode.NORMAL);
                    }
                })
                .start(tweenManager);
    }

    public void rockDestroyRoutine() {
        soundLink.play(3);
        Timeline.createSequence()
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_POS, 10f)
                        .target(getX() + 1, getY()))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_POS, 10f)
                        .target(getX() - 1, getY()))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_POS, 10f)
                        .target(getX() + 1, getY()))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_POS, 10f)
                        .target(getX() - 1, getY()))
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_POS, 50f)
                        .target(getX(), getY() - 25))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        setAnimation(Animation.PlayMode.NORMAL);
                    }
                })
        .start(tweenManager);
    }
}

