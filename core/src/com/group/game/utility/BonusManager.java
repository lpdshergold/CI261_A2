package com.group.game.utility;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.group.game.bodies.PlayerCharacter;
import com.group.game.bodies.PowerUpSprite;

import java.util.Arrays;
import java.util.Collections;

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
    private Float[] spawnVectorsX = {10.0f, 20.0f, 30.0f, 40.0f, 50.0f};
    private Float[] spawnVectorsY = {5.0f, 7.5f, 10.0f, 12.5f, 15.0f};
    public Vector2 randomSpawnVector;

    public BonusManager(PlayerCharacter playerCharacter) {
        this.playerCharacter = playerCharacter;
        for(int i = 0; i < bonusCollection.length; i++) {
            // PowerUpSprite requires there to be a string for the power up, texture and a position
            bonusCollection[i] = new PowerUpSprite(POWER_UP_ATLAS_PATH, TINY, null);
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

    private Vector2 calcNextPos() {
        float vectorPartX;
        float vectorPartY;

        Collections.shuffle(Arrays.asList(spawnVectorsX));
        Collections.shuffle(Arrays.asList(spawnVectorsY));

        vectorPartX = spawnVectorsX[0];
        vectorPartY = spawnVectorsY[0];

        randomSpawnVector.add(vectorPartX, vectorPartY);

        return randomSpawnVector;
    }

    private void nextBonusTimer(float dt) {}
}
