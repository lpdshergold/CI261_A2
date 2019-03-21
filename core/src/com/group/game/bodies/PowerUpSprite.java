package com.group.game.bodies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.group.game.utility.TweenData;

import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class PowerUpSprite extends AnimatedSprite {
    private TweenData tweenData;
    private TweenManager tweenManager;
    private float animationTime;
    private int ttl;
    private float timeCount;
    private boolean display = false;
    private TweenCallback callback;

    public PowerUpSprite(String atlasString, Texture t, Vector2 pos) {
        super(atlasString, t, pos);
    }
}
