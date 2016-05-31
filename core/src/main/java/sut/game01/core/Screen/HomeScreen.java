package sut.game01.core.Screen;

import playn.core.*;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import static playn.core.PlayN.*;

import tripleplay.ui.*;

public class HomeScreen extends UIScreen{
    public static final Font TITLE_FONT = graphics().createFont("Helvetica",Font.Style.PLAIN,24);
    private final ScreenStack ss;
    private Root root;
    public final TestScreen testScreen;   
    public final SettingScreen settingScreen; 
    private final ImageLayer bg;
    private final ImageLayer logo;
    private final ImageLayer play;
    private final ImageLayer setting;
    public static Sound soundbg;
    public static Sound soundClick;
    private final WaitScreen ws;

    public HomeScreen(final ScreenStack ss) {
        this.ss = ss;
        this.testScreen = new TestScreen(ss);
        this.settingScreen = new SettingScreen(ss);
        this.ws = new WaitScreen(ss);

        soundbg = assets().getSound("sounds/soundbg");
        soundClick = assets().getSound("sounds/click");

        Image bgImage = assets().getImage("images/bg.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image logoImage = assets().getImage("images/logo.png");
        this.logo = graphics().createImageLayer(logoImage);
        logo.setTranslation(120, 10);

        Image playImage = assets().getImage("images/play.png");
        this.play = graphics().createImageLayer(playImage);
        play.setTranslation(230, 280);

        Image settingImage = assets().getImage("images/setting.png");
        this.setting = graphics().createImageLayer(settingImage);
        setting.setTranslation(165, 360);

        play.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(ws); // push screen
                soundClick.play();
            }

        });
        setting.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                //System.exit(0);

                ss.push(settingScreen); // push screen
                soundClick.play();
            }
        });
        PlayN.keyboard().setListener(new Keyboard.Adapter(){
            @Override
            public void onKeyDown(Keyboard.Event event) {
                if(event.key() == Key.SPACE) {
                    super.onKeyDown(event);
                }
            }
        });
    }

    @Override
    public void wasShown(){

        super.wasShown();
        soundbg.play();

        soundbg.setLooping(true);

        soundbg.setVolume(0.3f);
        /*root = iface.createRoot(
                AxisLayout.vertical().gap(15),
                SimpleStyles.newSheet(), this.layer);

        /*root.addStyles(Style.BACKGROUND
            .is(Background.bordered(0xFFCCCCCC, 0xFF99CCFF, 5)
            .inset(5, 10)));

        root.setSize(width(), height());

        root.add(new Label("Event Driven Programming")
        .addStyles(Style.FONT.is(HomeScreen.TITLE_FONT)));


        root.add(new Button("PLAY").onClick(new UnitSlot() {
            public void onEmit() {
                ss.push(new TestScreen(ss));
            }
        }));*/
        this.layer.add(bg);
        this.layer.add(logo);
        this.layer.add(play);
        this.layer.add(setting);

    }


}
