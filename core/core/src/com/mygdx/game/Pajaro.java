package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Pajaro extends Game {
    SpriteBatch batch;
    BitmapFont smallFont, bigFont;
    AssetManager manager;
    int topScore;
    int lastScore;

    public void create() {
        manager = new AssetManager();
        manager.load("pajaro.png", Texture.class);
        manager.load("bck.png", Texture.class);
        manager.load("Default.png", Texture.class);
        manager.load("plataforma.png", Texture.class);
        manager.load("awesome.png", Texture.class);
        manager.load("moonster.png", Texture.class);
        manager.load("background.png", Texture.class);
        manager.load("muerto.png", Texture.class);
        manager.load("bichomovimiento.png", Texture.class);
        manager.load("jumpsound.mp3", Sound.class);
        manager.load("falling.mp3", Sound.class);
        manager.load("jumponmonster.mp3", Sound.class);

        topScore = 0;
        lastScore = 0;
        manager.finishLoading();


        batch = new SpriteBatch();
        FreeTypeFontGenerator generator = new  FreeTypeFontGenerator(Gdx.files.internal("8bitOperatorPlus-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 20;
        params.borderWidth = 2;
        params.borderColor = Color.BLACK;
        params.color = Color.WHITE;
        smallFont = generator.generateFont(params); // font size 22 pixels
        params.size = 50;
        params.borderWidth = 5;
        bigFont = generator.generateFont(params); // font size 50 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        this.setScreen(new MainMenuScreen(this));
    }
    public void render() {
        super.render(); // important!
    }
    public void dispose() {
    }

}


