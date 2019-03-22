package com.group.game.utility;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.group.game.bodies.PlayerCharacter;
import com.group.game.bodies.PowerUpSprite;

import static com.group.game.utility.Constants.MAX_BONUS_SPRITES;
import static com.group.game.utility.Constants.MAX_TIME_TO_NEXT_BONUS;
import static com.group.game.utility.Constants.POWERUP_VALUE;
import static com.group.game.utility.Constants.POWER_UP_ATLAS_PATH;
import static com.group.game.utility.Constants.TINY;

public class BonusManager {
    PowerUpSprite[] bonusCollection = new PowerUpSprite[MAX_BONUS_SPRITES];
    PlayerCharacter playerCharacter;
    private int bonusSpriteToDisplay;
    private float timeCount;
    private float timeToNextBonus = MAX_TIME_TO_NEXT_BONUS;
    public static boolean handlingCollision = false;
    private Vector2 previousBonusPosition;

    public BonusManager(PlayerCharacter playerCharacter) {
        this.playerCharacter = playerCharacter;
        for(int i = 0; i < bonusCollection.length; i++) {
            bonusCollection[i] = new PowerUpSprite(POWER_UP_ATLAS_PATH, TINY);
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
        for(PowerUpSprite ps : bonusCollection) {
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
        bonusCollection[bonusSpriteToDisplay].startRoutine(calcNextPos());
    }

    private Vector2 calcNextPos() { return Vector2.X;}

    private void nextBonusTimer(float dt) {}
}
