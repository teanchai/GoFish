package sut.game01.core.Screen;

import playn.core.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class SettingScreen extends Screen {
	private final ScreenStack ss;
    private final ImageLayer bg;
    private final ImageLayer backButton;
    private final ImageLayer setting;
    private final ImageLayer sound;
    private final ImageLayer on;
    private final ImageLayer slash;
    private final ImageLayer off;
    private final ImageLayer music;
    private final ImageLayer on1;
    private final ImageLayer slash1;
    private final ImageLayer off1;
    private boolean offmusic=false;


    public SettingScreen(final ScreenStack ss) {
		this.ss = ss;
        
		Image bgImage = assets().getImage("images/bg.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image settingImage = assets().getImage("images/setting.png");
        this.setting = graphics().createImageLayer(settingImage);
        setting.setTranslation(180, 10);

        Image backImage = assets().getImage("images/backbutton.png");
        this.backButton = graphics().createImageLayer(backImage);
        backButton.setTranslation(300, 310);

        Image soundImage = assets().getImage("images/sound.png");
        this.sound = graphics().createImageLayer(soundImage);
        sound.setTranslation(125, 165);

        Image onImage = assets().getImage("images/on.png");
        this.on = graphics().createImageLayer(onImage);
        on.setTranslation(355, 165);

        Image slashImage = assets().getImage("images/slash.png");
        this.slash = graphics().createImageLayer(slashImage);
        slash.setTranslation(435, 165);

        Image offImage = assets().getImage("images/off.png");
        this.off = graphics().createImageLayer(offImage);
        off.setTranslation(475, 165);

        Image musicImage = assets().getImage("images/music.png");
        this.music = graphics().createImageLayer(musicImage);
        music.setTranslation(125, 245);

        Image on1Image = assets().getImage("images/on.png");
        this.on1 = graphics().createImageLayer(on1Image);
        on1.setTranslation(355, 245);

        Image slash1Image = assets().getImage("images/slash.png");
        this.slash1 = graphics().createImageLayer(slash1Image);
        slash1.setTranslation(435, 245);

        Image off1Image = assets().getImage("images/off.png");
        this.off1 = graphics().createImageLayer(off1Image);
        off1.setTranslation(475, 245);


        backButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top()); // pop screen
            }
        });

        off.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                HomeScreen.soundbg.stop();
                offmusic=true;
            }
        });
        if(offmusic) {
            on.addListener(new Mouse.LayerAdapter() {
                @Override
                public void onMouseUp(Mouse.ButtonEvent event) {
                    HomeScreen.soundbg.play();
                }
            });
        }

	}

	@Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(setting);
        this.layer.add(backButton);
        this.layer.add(sound);
        this.layer.add(on);
        this.layer.add(off);
        this.layer.add(slash);
        this.layer.add(slash1);
        this.layer.add(music);
        this.layer.add(on1);
        this.layer.add(off1);
    }
}