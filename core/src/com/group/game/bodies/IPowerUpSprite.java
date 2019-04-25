package com.group.game.bodies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public interface IPowerUpSprite {

    public boolean isDisplayed();

    public void setDisplayed(boolean d);

    public void draw(SpriteBatch sb);

    public void update(float stateTime);

    public Rectangle getBoundingRectangle();

    public void bonusRoutine();

}
