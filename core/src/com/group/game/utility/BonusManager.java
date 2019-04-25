package com.group.game.utility;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.group.game.bodies.IPowerUpSprite;
import com.group.game.bodies.PlayerCharacter;
import com.group.game.bodies.PowerDownSprite;
import com.group.game.bodies.PowerUpSprite;

import static com.group.game.utility.Constants.CLAMP_HEIGHT;
import static com.group.game.utility.Constants.MAX_BONUS_SPRITES;
import static com.group.game.utility.Constants.MAX_POS_TO_NEXT_BONUS;
import static com.group.game.utility.Constants.MAX_TIME_TO_NEXT_BONUS;
import static com.group.game.utility.Constants.MIN_POS_TO_NEXT_BONUS;
import static com.group.game.utility.Constants.MIN_TIME_TO_NEXT_BONUS;
import static com.group.game.utility.Constants.POWERUP_VALUE;
import static com.group.game.utility.Constants.POWER_DOWN_BAD_BOOST_PATH;
import static com.group.game.utility.Constants.POWER_DOWN_BARREL_PATH;
import static com.group.game.utility.Constants.POWER_DOWN_ENEMY_PATH;
import static com.group.game.utility.Constants.POWER_DOWN_ROCK_PATH;
import static com.group.game.utility.Constants.POWER_UP_BADGE_PATH;
import static com.group.game.utility.Constants.POWER_UP_GOOD_BOOST_PATH;
import static com.group.game.utility.Constants.POWER_UP_PLAYER_PATH;
import static com.group.game.utility.Constants.TINY;

public class BonusManager {
    IPowerUpSprite[] bonusCollection = new PowerUpSprite[MAX_BONUS_SPRITES];
    PlayerCharacter playerCharacter;
    private int bonusSpriteToDisplay;
    private float timeCount;
    private float timeToNextBonus = MAX_TIME_TO_NEXT_BONUS;
    public static boolean handlingCollision = false;
    private Vector2 previousBonusPosition;
    private float flipX = -3;

    public BonusManager(PlayerCharacter playerCharacter) {
        this.playerCharacter = playerCharacter;
        for(int i = 0; i < bonusCollection.length; i++) {
            if(i == 0) {
                bonusCollection[i] = new PowerUpSprite(POWER_UP_BADGE_PATH, TINY, new Vector2(250, 100 * i));
            } else if(i == 1) {
                bonusCollection[i] = new PowerDownSprite(POWER_DOWN_ROCK_PATH, TINY, new Vector2(250, 100 * i));
            } else if (i == 2) {
                bonusCollection[i] = new PowerUpSprite(POWER_UP_GOOD_BOOST_PATH, TINY, new Vector2(250, 100 * i));
            } else if (i == 3) {
                bonusCollection[i] = new PowerDownSprite(POWER_DOWN_BARREL_PATH, TINY, new Vector2(250, 100 * i));
            } else if (i == 4) {
                bonusCollection[i] = new PowerUpSprite(POWER_UP_PLAYER_PATH, TINY, new Vector2(250, 100 * i));
            }else if (i == 5) {
                bonusCollection[i] = new PowerDownSprite(POWER_DOWN_BAD_BOOST_PATH, TINY, new Vector2(100, 100 * i));
            }else {
                bonusCollection[i] = new PowerDownSprite(POWER_DOWN_ENEMY_PATH, TINY, new Vector2(100, 100 * i));
            }
        }
    }

    public void draw(SpriteBatch batch) {
        for(int i = 0; i < bonusCollection.length; i++) {
            if(bonusCollection[i].isDisplayed()) {
                bonusCollection[i].draw(batch);
            }
        }
    }

    public void update(float stateTime) {
        for(IPowerUpSprite ps : bonusCollection) {
            ps.update(stateTime);
            if(ps.isDisplayed() && !handlingCollision) {
                if(Intersector.overlaps(ps.getBoundingRectangle(), playerCharacter.getBoundingRectangle())) {
                    handlingCollision = true;
                    GameData.getInstance().addScore(POWERUP_VALUE);
                    ps.bonusRoutine();
                }
            }
        }
        nextBonusTimer(stateTime);
    }

    public void positionNextBonus() {
        while(bonusCollection[bonusSpriteToDisplay].isDisplayed()) {
            bonusSpriteToDisplay = (bonusSpriteToDisplay + 1) % bonusCollection.length;
        }
        bonusCollection[bonusSpriteToDisplay].setDisplayed(true);


        // Don't need to calculate the next position as of yet - maybe implement closer to the deadline if we have time
        // Using i in bonusCollection[i] to calculate the y position for the bonus sprites
        // bonusCollection[bonusSpriteToDisplay].startRoutine(calcNextPos());
    }

    private Vector2 calcNextPos(){
        Vector2 tempPos = playerCharacter.getWorldPosition();
        Vector2 position = new Vector2(tempPos);
        position.x -= MIN_POS_TO_NEXT_BONUS +(float)
                (Math.random() * (MAX_POS_TO_NEXT_BONUS));
        position.y += MIN_POS_TO_NEXT_BONUS +(float)
                (Math.random() * (MAX_POS_TO_NEXT_BONUS));
        //clamp Y Axis
        position.y=clamp(position.y,tempPos.y,CLAMP_HEIGHT);
        //Unclutter X Axis
        if(previousBonusPosition != null){
            if(checkX(position.x)){
                flipX = flipX  * -flipX;
                position.x+=flipX;
                position.y+=3;
            }
        }
        previousBonusPosition = position;
        return position;
    }


    private void nextBonusTimer(float dt) {
        timeCount += dt;
        if (timeCount >= 1) {
            if (timeToNextBonus > 0) {
                timeToNextBonus--;
            } else {
                positionNextBonus();
                timeToNextBonus = (int)MIN_TIME_TO_NEXT_BONUS +(int)
                        (Math.random() * (MAX_TIME_TO_NEXT_BONUS - MIN_TIME_TO_NEXT_BONUS));
            }
            timeCount = 0;
        }
    }

    private float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    private boolean checkX(float x){
        if(x> previousBonusPosition.x && x <previousBonusPosition.x + 1
                || x< previousBonusPosition.x && x > previousBonusPosition.x -1)
            return true;
        return false;
    }
}
