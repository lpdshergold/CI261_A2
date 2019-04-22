package com.group.game.bodies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.group.game.utility.BonusManager;
import com.group.game.utility.TweenData;
import com.group.game.utility.UniversalResource;

import java.util.Arrays;
import java.util.Collections;

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
    private boolean displayed = false;
    private Float[] spawnVectorsX = {10.0f, 20.0f, 30.0f, 40.0f, 50.0f};
    private Float[] spawnVectorsY = {5.0f, 7.5f, 10.0f, 12.5f, 15.0f};
    public Vector2 randomSpawnVector;

    public boolean isDisplayed() {return displayed;}

    public PowerUpSprite(String atlasString, Texture t, Vector2 pos) {
        super(atlasString, t, pos);
        // Alpha set a 0 means the sprite cannot be seen
        this.setAlpha(0);
        ttl = setTimeToLive(0);
        callback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                setDisplayed(false);
                initTweenData();
                BonusManager.handlingCollision = false;
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

    private int setTimeToLive(int t) {
        ttl = t;
        return ttl;
    }

    public void setDisplayed(boolean d) {
        displayed = d;
    }

    public Vector2 createRandomSpawnVector () {

        float vectorPartX;
        float vectorPartY;

        Collections.shuffle(Arrays.asList(spawnVectorsX));
        Collections.shuffle(Arrays.asList(spawnVectorsY));

        vectorPartX = spawnVectorsX[0];
        vectorPartY = spawnVectorsY[0];

        randomSpawnVector.add(vectorPartX, vectorPartY);

        return randomSpawnVector;

    }
}

