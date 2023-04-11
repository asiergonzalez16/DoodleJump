package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Platform extends Actor {
    Rectangle bounds;
    boolean upsideDown;
    AssetManager manager;
    GameScreen gameScreen;

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    int speedX;
    Platform()
    {
        setSize(64, 230);
        bounds = new Rectangle();
        setVisible(true);

    }
    @Override
    public void act(float delta)
    {
        if(this.getX() >680){
            this.speedX = -60;
        }
        if(this.getX()<120){
            this.speedX = 60;
        }
        moveBy(speedX*delta,0);
        bounds.set(getX(), getY(), getWidth(), getHeight());
        //añadir moveby y actualizar bounds (setBounds)
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(manager.get("plataforma.png", Texture.class), getX(), getY()- gameScreen.scrollY);
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

