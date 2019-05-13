package com.group.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.group.game.TBWGame;
import com.group.game.bodies.PlayerCharacter;
import com.group.game.bodies.PowerDownSprite;
import com.group.game.bodies.PowerUpSprite;
import com.group.game.physics.WorldManager;
import com.group.game.utility.CameraManager;
import com.group.game.utility.Constants;
import com.group.game.utility.GameData;
import com.group.game.utility.HUD;
import com.group.game.utility.UniversalResource;

import static com.group.game.utility.Constants.PDown_BAD_BOOST_START;
import static com.group.game.utility.Constants.PDown_BARREL_START;
import static com.group.game.utility.Constants.PDown_ENEMY_START;
import static com.group.game.utility.Constants.PDown_ROCK_START;
import static com.group.game.utility.Constants.PLAYER_ATLAS_PATH;
import static com.group.game.utility.Constants.POWERDOWN_BAD_BOOST_VALUE;
import static com.group.game.utility.Constants.POWERDOWN_BARREL_VALUE;
import static com.group.game.utility.Constants.POWERDOWN_ENEMY_VALUE;
import static com.group.game.utility.Constants.POWERDOWN_ROCK_VALUE;
import static com.group.game.utility.Constants.POWERUP_BADGE_VALUE;
import static com.group.game.utility.Constants.POWERUP_BOOST_VALUE;
import static com.group.game.utility.Constants.POWERUP_PLAYER_VALUE;
import static com.group.game.utility.Constants.POWER_DOWN_BAD_BOOST_PATH;
import static com.group.game.utility.Constants.POWER_DOWN_BARREL_PATH;
import static com.group.game.utility.Constants.POWER_DOWN_ENEMY_PATH;
import static com.group.game.utility.Constants.POWER_DOWN_ROCK_PATH;
import static com.group.game.utility.Constants.POWER_UP_BADGE_PATH;
import static com.group.game.utility.Constants.POWER_UP_GOOD_BOOST_PATH;
import static com.group.game.utility.Constants.POWER_UP_PLAYER_PATH;
import static com.group.game.utility.Constants.PUp_BADGE_START;
import static com.group.game.utility.Constants.PUp_BOOST_START;
import static com.group.game.utility.Constants.PUp_PLAYER_START;
import static com.group.game.utility.Constants.SMALL;
import static com.group.game.utility.Constants.START_POSITION;
import static com.group.game.utility.Constants.TINY;
import static com.group.game.utility.Constants.UNITSCALE;
import static com.group.game.utility.Constants.VIRTUAL_HEIGHT;
import static com.group.game.utility.Constants.VIRTUAL_WIDTH;


/**
 * Created by gerard on 12/02/2017.
 */

public class GameScreen extends ScreenAdapter {
    private TBWGame game;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private PlayerCharacter smif;
    private PowerUpSprite badge, boost, player;
    private PowerDownSprite barrel ,badBoost, enemy, rock;
    private HUD gameHUD;
    private CameraManager cameraManager;
    private float frameDelta = 0;

    private boolean bc = true;

    public GameScreen(TBWGame tbwGame){this.game = tbwGame;}

    @Override
    public void resize(int width, int height) {
        game.camera.setToOrtho(false, VIRTUAL_WIDTH * UNITSCALE, VIRTUAL_HEIGHT * UNITSCALE);
        game.batch.setProjectionMatrix(game.camera.combined);
    }

    @Override
    public void show() {
        super.show();
        tiledMap = game.getAssetManager().get(Constants.BACKGROUND);
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(this.tiledMap,UNITSCALE);
        orthogonalTiledMapRenderer.setView(game.camera);
        if(!WorldManager.isInitialised()){WorldManager.initialise(game,tiledMap);}
        //player
        smif = new PlayerCharacter(PLAYER_ATLAS_PATH,TINY,START_POSITION);

        // PowerUpSprite + routine to shrink the sprites
        badge = new PowerUpSprite(POWER_UP_BADGE_PATH, SMALL, PUp_BADGE_START);
        badge.runningRoutines("shrink");

        boost = new PowerUpSprite(POWER_UP_GOOD_BOOST_PATH, SMALL, PUp_BOOST_START);
        boost.runningRoutines("shrink");

        player = new PowerUpSprite(POWER_UP_PLAYER_PATH, SMALL, PUp_PLAYER_START);
        player.runningRoutines("shrink");

        // PowerDownSprite + routine to shrink the sprites
        barrel = new PowerDownSprite(POWER_DOWN_BARREL_PATH, SMALL, PDown_BARREL_START);
        barrel.runningRoutines("shrink");

        badBoost = new PowerDownSprite(POWER_DOWN_BAD_BOOST_PATH, SMALL, PDown_BAD_BOOST_START);
        badBoost.runningRoutines("shrink");

        enemy = new PowerDownSprite(POWER_DOWN_ENEMY_PATH, SMALL, PDown_ENEMY_START);
        enemy.runningRoutines("shrink");

        rock = new PowerDownSprite(POWER_DOWN_ROCK_PATH, SMALL, PDown_ROCK_START);
        rock.runningRoutines("shrink");


        cameraManager = new CameraManager(game.camera,tiledMap);
        cameraManager.setTarget(smif);
        gameHUD = new HUD(game.batch,smif,game);
    }

    @Override
    public void render(float delta) {
        frameDelta += delta;

        // PowerUpSprites
        badge.update(frameDelta);
        boost.update(frameDelta);
        player.update(frameDelta);

        // PowerDownSprites
        barrel.update(frameDelta);
        badBoost.update(frameDelta);
        enemy.update(frameDelta);
        rock.update(frameDelta);

        // Player and gameHUD
        smif.update(frameDelta);
        gameHUD.update(delta);

        // Used to update PowerUp/DownSprite routines
        UniversalResource.getInstance().tweenManager.update(frameDelta);

        if(Intersector.overlaps(badge.getBoundingRectangle(), smif.getBoundingRectangle())) {
            // Check if handling collision equals true
            if (badge.badgeCol == true) {
                // Once powerUp is hit once, turn false
                badge.badgeCol = false;
                GameData.getInstance().addScore(POWERUP_BADGE_VALUE);
                badge.runningRoutines("badgeDestroy");
            }
        } else if(Intersector.overlaps(boost.getBoundingRectangle(), smif.getBoundingRectangle())) {
            if(boost.boostCol == true) {
                boost.boostCol = false;
                GameData.getInstance().addScore(POWERUP_BOOST_VALUE);
                boost.runningRoutines("boostDestroy");
            }
        } else if(Intersector.overlaps(player.getBoundingRectangle(), smif.getBoundingRectangle())) {
            if(player.playerCol == true) {
                player.playerCol = false;
                GameData.getInstance().addScore(POWERUP_PLAYER_VALUE);
                player.runningRoutines("playerDestroy");
            }
        }
        else if(Intersector.overlaps(barrel.getBoundingRectangle(), smif.getBoundingRectangle())) {
            if(barrel.barrelCol == true) {
                barrel.barrelCol = false;
                GameData.getInstance().addScore(POWERDOWN_BARREL_VALUE);
                barrel.runningRoutines("barrelDestroy");
            }
        } else if(Intersector.overlaps(badBoost.getBoundingRectangle(), smif.getBoundingRectangle())) {
            if(badBoost.badBoostCol == true) {
                badBoost.badBoostCol = false;
                GameData.getInstance().addScore(POWERDOWN_BAD_BOOST_VALUE);
                badBoost.runningRoutines("badBoostDestroy");
            }
        } else if(Intersector.overlaps(enemy.getBoundingRectangle(), smif.getBoundingRectangle())) {
            if(enemy.enemyCol == true) {
                enemy.enemyCol = false;
                GameData.getInstance().addScore(POWERDOWN_ENEMY_VALUE);
                enemy.runningRoutines("enemyDestroy");
            }
        } else if(Intersector.overlaps(rock.getBoundingRectangle(), smif.getBoundingRectangle())) {
            if(rock.rockCol == true) {
                rock.rockCol = false;
                GameData.getInstance().addScore(POWERDOWN_ROCK_VALUE);
                rock.runningRoutines("rockDestroy");
            }
        }


        game.batch.setProjectionMatrix(game.camera.combined);
        clearScreen();
        draw();
        WorldManager.getInstance().doPhysicsStep(delta);
    }

    private void draw() {
       orthogonalTiledMapRenderer.setView(game.camera);
       orthogonalTiledMapRenderer.render();
        cameraManager.update();
        game.batch.begin();

        // Character
        smif.draw(game.batch);

        // PowerUpSprites
        badge.draw(game.batch);
        boost.draw(game.batch);
        player.draw(game.batch);

        // PowerDownSprites
        barrel.draw(game.batch);
        badBoost.draw(game.batch);
        enemy.draw(game.batch);
        rock.draw(game.batch);

        game.batch.end();
        gameHUD.stage.draw();
        WorldManager.getInstance().debugRender();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
                Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}