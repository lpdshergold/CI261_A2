package com.group.game.utility;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class UniversalResource {

    public TweenManager tweenManager;

    private static UniversalResource instance;

    public static UniversalResource getInstance()
    {
        if (instance == null)
        {
            instance = new UniversalResource();
        }
        return instance;
    }

    private UniversalResource()
    {
        configure();
    }

    private void configure()
    {
        Tween.setCombinedAttributesLimit(4);
        tweenManager = new TweenManager();
        Tween.registerAccessor(utility.TweenData.class, new utility.TweenDataAccessor());
    }
}
