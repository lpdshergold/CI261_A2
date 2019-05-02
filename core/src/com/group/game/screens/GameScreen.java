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
import com.group.game.bodies.PowerUpSprite;
import com.group.game.physics.WorldManager;
import com.group.game.utility.CameraManager;
import com.group.game.utility.Constants;
import com.group.game.utility.GameData;
import com.group.game.utility.HUD;

import static com.group.game.utility.Constants.PLAYER_ATLAS_PATH;
import static com.group.game.utility.Constants.POWERUP_BADGE_VALUE;
import static com.group.game.utility.Constants.POWERUP_BOOST_VALUE;
import static com.group.game.utility.Constants.POWERUP_PLAYER_VALUE;
import static com.group.game.utility.Constants.POWER_UP_BADGE_PATH;
import static com.group.game.utility.Constants.POWER_UP_GOOD_BOOST_PATH;
import static com.group.game.utility.Constants.POWER_UP_PLAYER_PATH;
import static com.group.game.utility.Constants.PUp_BADGE_START;
import static com.group.game.utility.Constants.PUp_BOOST_START;
import static com.group.game.utility.Constants.PUp_PLAYER_START;
import static com.group.game.utility.Constants.SMALL;
import static com.group.game.utility.Constants.START_POSITION;
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
    private HUD gameHUD;
    // private BonusManager bonusManager;
    private CameraManager cameraManager;
    private float frameDelta = 0;

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
        smif = new PlayerCharacter(PLAYER_ATLAS_PATH,SMALL,START_POSITION);


        //need to remake some of the powerups and powerdowns from their original file, to .atlas and .png
        badge = new PowerUpSprite(POWER_UP_BADGE_PATH, SMALL, PUp_BADGE_START);
        boost = new PowerUpSprite(POWER_UP_GOOD_BOOST_PATH, SMALL, PUp_BOOST_START);
        player = new PowerUpSprite(POWER_UP_PLAYER_PATH, SMALL, PUp_PLAYER_START);

        cameraManager = new CameraManager(game.camera,tiledMap);
        cameraManager.setTarget(smif);
        gameHUD = new HUD(game.batch,smif,game);
    }

    @Override
    public void render(float delta) {
        frameDelta += delta;
        badge.update(frameDelta);
        boost.update(frameDelta);
        player.update(frameDelta);
        smif.update(frameDelta);
        gameHUD.update(delta);
        // bonusManager.update(delta);

        if(Intersector.overlaps(badge.getBoundingRectangle(), smif.getBoundingRectangle())) {
            badge.handlingCollision = true;
            GameData.getInstance().addScore(POWERUP_BADGE_VALUE);
            badge.destroyRoutine();
        } else if(Intersector.overlaps(boost.getBoundingRectangle(), smif.getBoundingRectangle())) {
            boost.handlingCollision = true;
            GameData.getInstance().addScore(POWERUP_BOOST_VALUE);
            boost.destroyRoutine();
        } else if(Intersector.overlaps(player.getBoundingRectangle(), smif.getBoundingRectangle())) {
            player.handlingCollision = true;
            GameData.getInstance().addScore(POWERUP_PLAYER_VALUE);
            player.destroyRoutine();
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
        smif.draw(game.batch);

        badge.draw(game.batch);
        boost.draw(game.batch);
        player.draw(game.batch);

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