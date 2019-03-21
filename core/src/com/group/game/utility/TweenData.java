package com.group.game.utility;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class TweenData {

    private Vector2 xy;
    private Color color;
    private float scale;
    private float rotation;

    public TweenData()
    {
        xy = new Vector2();
        color = new Color();
    }

    //Accessors
    public float getScale() {return scale;}
    public Vector2 getXy() {return xy;}
    public Color getColor() {return color;}
    public float getRotation() {return rotation;}

    //Mutators
    public void setXy(Vector2 v ) {xy.x = v.x; xy.y = v.y;}
    public void setColor(Color c) {color = c;}
    public void setScale(float s) {scale = s;}
    public void setRotation(float r) {rotation = r;}

}
