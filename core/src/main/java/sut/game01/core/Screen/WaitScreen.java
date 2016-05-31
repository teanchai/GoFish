package sut.game01.core.Screen;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by Teanchai on 5/28/2016.
 */
public class WaitScreen extends Screen {
    private final ImageLayer bg;
    private final ScreenStack ss;
    private final ImageLayer reset;
    private final ImageLayer word;

    public WaitScreen(final ScreenStack ss){
        this.ss = ss;
        Image bgImage = assets().getImage("images/bg.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image wordImage = assets().getImage("images/word.png");
        this.word = graphics().createImageLayer(wordImage);
        word.setTranslation(0, 100);

        Image resetImage = assets().getImage("images/playb.png");
        this.reset = graphics().createImageLayer(resetImage);
        reset.setTranslation(280, 220);
        //HomeScreen.soundbg.play();

        reset.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(new TestScreen(ss)); // push screen
                HomeScreen.soundClick.play();
            }
        });

    }
    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(word);
        this.layer.add(reset);
    }
}
