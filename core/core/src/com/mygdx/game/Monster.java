package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Monster extends Actor {
    Rectangle bounds;
    boolean upsideDown;
    AssetManager manager;
    GameScreen gameScreen;
    float timer;

    Monster()
    {
        setSize(170, 100);
        bounds = new Rectangle();
        setVisible(true);
    }
    @Override
    public void act(float delta) {
        timer += delta;
        if(timer >=1) timer = 0;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(timer <0.5f){
            batch.draw(manager.get("moonster.png", Texture.class), getX(), getY()- gameScreen.scrollY);
        }else{
            batch.draw(manager.get("bichomovimiento.png", Texture.class), getX(), getY()- gameScreen.scrollY);
        }

    }
    public Rectangle getBounds() {
        return bounds;
    }
    public boolean isUpsideDown() {
        return upsideDown;
    }
    public void setUpsideDown(boolean upsideDown) {
        this.upsideDown = upsideDown;
    }
    public void setManager(AssetManager manager) {
        this.manager = manager;
    }
    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

}
