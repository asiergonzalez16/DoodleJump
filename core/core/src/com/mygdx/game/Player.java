package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    Rectangle bounds;
    AssetManager manager;
    float gravity;
    float VelX, VelY;
    boolean vivo;
    GameScreen gameScreen;

    Player() {
        VelY = 0;
        gravity = 850f;
        setX(200);
        setY(280 / 2 - 64 / 2);
        setSize(64,45);
        bounds = new Rectangle();
        vivo = true;
         //DUDA
    }
    @Override
    public void act(float delta)
    {
        //Actualitza la posició del jugador amb la velocitat vertical
        moveBy(VelX*delta,VelY*delta);

        if(getX() < -54) setX(800);
        if(getX()>800) setX(-54);
        if(getY() < 10 && VelY < 0) VelY = 1600;

        //Actualitza la velocitat vertical amb la gravetat
        VelY -= gravity * delta;
        bounds.set(getX(), getY(), getWidth(), getHeight());
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(vivo){
            batch.draw(manager.get("pajaro.png", Texture.class), getX()-32, getY()-gameScreen.scrollY);
        }else{
            batch.draw(manager.get("muerto.png", Texture.class), getX()-32, getY()-gameScreen.scrollY);
        }

    }
    public Rectangle getBounds() {
        return bounds;
    }
    public void setManager(AssetManager manager) {
        this.manager = manager;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
}
