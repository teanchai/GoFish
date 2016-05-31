package sut.game01.core;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;


public class GameOver extends Screen {
    private final ScreenStack ss;
    private final ImageLayer bg;
    private final ImageLayer died;


    public GameOver(final ScreenStack ss) {
        this.ss = ss;

        Image bgImage = assets().getImage("images/bg.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image diedImage = assets().getImage("images/died.png");
        this.died = graphics().createImageLayer(diedImage);
        died.setTranslation(50, 70);


    }

    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(died);
    }
}