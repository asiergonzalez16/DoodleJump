package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;

import jdk.internal.net.http.common.Log;

public class GameScreen implements Screen {
    final Pajaro game;
    OrthographicCamera camera;
    Stage stage;
    Player player;
    float scrollY;
    Array<Platform> obstacles;
    float lastObstacleHeight;
    boolean primeravez = false;
    boolean isFalling;
    boolean dead;
    float score;
    float lastMonster;
    Array<Monster> monsters;

    public GameScreen(final Pajaro gam) {
        this.game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 1600);
        player = new Player();
        player.setGameScreen(this);
        player.setManager(game.manager);
        stage = new Stage();
        stage.getViewport().setCamera(camera);
        stage.addActor(player);
        scrollY = 0;
        lastMonster = 10;
        obstacles = new Array<>();
        isFalling = true;
        dead = false;
        score = 0;
        lastObstacleHeight = 0;
        monsters = new Array<>();
    }

    @Override
    public void render(float delta) {

        //float accelY = Gdx.input.getAccelerometerY();
        //float accelZ = Gdx.input.getAccelerometerZ();
        ScreenUtils.clear(0.3f, 0.8f, 0.8f, 1);
        camera.update();
// tell the SpriteBatch to render in the
// coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);
// begin a new batch
        game.batch.begin();

        if (player.vivo) {
            float accelX = Gdx.input.getAccelerometerX();
            if (player.VelY < 0) {
                isFalling = true;
            } else {
                isFalling = false;
            }

            player.VelX = accelX * -200;
            game.batch.draw(game.manager.get("background.png", Texture.class), 0, 0);

            if (scrollY < player.getY() - 700) {
                scrollY = player.getY() - 700;
            }

            /*if (player.getY() > lastMonsterHeight) {
                Monster monster = new Monster();
                monster.setX(random());
                monster.setY(lastMonsterHeight + 2050);
                monster.setWidth(170);
                monster.setHeight(100);
                lastMonsterHeight = monster.getY();
                monster.setManager(game.manager);
                monster.setGameScreen(this);
                monster.bounds.set(monster.getX(), monster.getY(), monster.getWidth(), monster.getHeight());
                monsters.add(monster);
                stage.addActor(monster);
            }*/
            if(lastMonster > 0){
                Platform platform = new Platform();
                platform.setX(random());
                platform.setSpeedX(60);
                platform.setY(lastObstacleHeight + 150);
                platform.setWidth(117);
                platform.setHeight(33);
                lastObstacleHeight = platform.getY();
                platform.setManager(game.manager);
                platform.setGameScreen(this);
                platform.bounds.set(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight());
                obstacles.add(platform);
                stage.addActor(platform);
                lastMonster--;
            }else{
                Monster monster = new Monster();
                monster.setX(random());
                monster.setY(lastObstacleHeight+150);
                monster.setWidth(170);
                monster.setHeight(100);
                monster.setManager(game.manager);
                lastObstacleHeight = monster.getY();
                monster.setGameScreen(this);
                monster.bounds.set(monster.getX(), monster.getY(), monster.getWidth(), monster.getHeight());
                monsters.add(monster);
                stage.addActor(monster);
                lastMonster = 10;
            }

            Iterator<Platform> iter = obstacles.iterator();
            while (iter.hasNext()) {
                Platform platform = iter.next();

                if (platform.getY() < scrollY) {
                    obstacles.removeValue(platform, true);
                }
                if (platform.getBounds().overlaps(player.getBounds()) && isFalling && player.getY() > platform.getY()) {
                    game.manager.get("jumpsound.mp3", Sound.class).play();
                    player.VelY = 1000;
                    score += 1472;
                }
            }

            Iterator<Monster> mons = monsters.iterator();
            while (mons.hasNext()) {
                Monster monster = mons.next();
                if (monster.getBounds().overlaps(player.getBounds()) && player.getY() > monster.getY()) {
                    game.manager.get("jumponmonster.mp3", Sound.class).play();
                    player.VelY = 2000;
                    monsters.removeValue(monster, true);
                    monster.remove();
                    score += 3129;
                }
                if (monster.getBounds().overlaps(player.getBounds()) && player.getY() < monster.getY()) {
                    game.manager.get("falling.mp3", Sound.class).play();
                    player.vivo = false;
                }
                if (monster.getY() < scrollY - monster.getHeight()) {
                    monsters.removeValue(monster, true);
                    monster.remove();
                }
            }
            if (player.getY() < scrollY - 100) {
                dead = true;
            }

        }else{
            game.batch.draw(game.manager.get("background.png", Texture.class), 0, 0);
            player.VelY = -1000;
            player.VelX = 0;
            player.setX(player.getX());
            if(player.getY() < scrollY){
                dead = true;
            }
        }
        if (dead) {
            game.lastScore = (int) score;
            if (game.lastScore > game.topScore)
                game.topScore = game.lastScore;
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
        game.batch.end();
        stage.getBatch().setProjectionMatrix(camera.combined);
        stage.draw();
        stage.act();
        game.batch.begin();

        game.smallFont.draw(game.batch, "Score: " + (int) score, 20, 1570);
        game.batch.end();
    }
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
    public int random(){
        return MathUtils.random(0, 680);
    }
    private void spawnObstacle() {
        primeravez = true;
      //
       /* Platform platform2 = new Platform();
        platform2.setX(random());
        platform2.setY(150);
        platform2.setManager(game.manager);
        obstacles.add(platform2);
        stage.addActor(platform2);

        Platform platform3 = new Platform();
        platform3.setX(random());
        platform3.setY(250);
        platform3.setManager(game.manager);
        obstacles.add(platform3);
        stage.addActor(platform3);

        Platform platform4 = new Platform();
        platform4.setX(random());
        platform4.setY(350);
        platform4.setManager(game.manager);
        obstacles.add(platform4);
        stage.addActor(platform4);

        Platform platform5 = new Platform();
        platform5.setX(random());
        platform5.setY(450);
        platform5.setManager(game.manager);
        obstacles.add(platform5);
        stage.addActor(platform5);

        Platform platform6 = new Platform();
        platform6.setX(random());
        platform6.setY(550);
        platform6.setManager(game.manager);
        obstacles.add(platform6);
        stage.addActor(platform6);

        Platform platform7 = new Platform();
        platform7.setX(random());
        platform7.setY(650);
        platform7.setManager(game.manager);
        obstacles.add(platform7);
        stage.addActor(platform7);

        Platform platform8 = new Platform();
        platform8.setX(random());
        platform8.setY(750);
        platform8.setManager(game.manager);
        obstacles.add(platform8);
        stage.addActor(platform8);
        */
    }
}