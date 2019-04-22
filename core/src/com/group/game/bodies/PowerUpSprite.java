package com.group.game.bodies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.group.game.utility.BonusManager;
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
    private boolean displayed = false;
 // private Float[] spawnVectorsX;
 // private Float[] spawnVectorsY;
 // private Vector2 randomSpawnVector;

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

    // private Vector2 createRandomSpawnVector () {               --> Creates 2 arrays of possible X positions
    //                                                                and Y positions, shuffles them and creates
    //     private float vectorPartX;                                 a Vector2 from element (0) of each.
    //     private float vectorPartY;
    //
    //     spawnVectorsX = {10.0, 20.0, 30.0, 40.0, 50.0};
    //     spawnVectorsY = {5.0, 7.5, 10.0, 12.5, 15.0};
    //
    //     Collections.shuffle(Arrays.asList(spawnVectorsX));     --> requires java.util.Collections
    //     Collections.shuffle(Arrays.asList(spawnVectorsY));     --> requires java.util.Collections
    //
    //     vectorPartX = spawnVectorsX.get(0);
    //     vectorPartY = spawnVectorsY.get(0);
    //
    //     randomSpawnVector.add(vectorPartX, vectorPartY);
    //
    //     return randomSpawnVector;
    // }

}

