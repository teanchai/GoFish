package sut.game01.core.Screen;

import org.jbox2d.common.Vec2;
import playn.core.*;
import playn.core.util.Clock;
import sut.game01.core.Scores.Number;
import sut.game01.core.Scores.Number1;
import sut.game01.core.character.Fish;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.*;


public class GameOver extends Screen {
    private final ScreenStack ss;
    private final ImageLayer bg;
    //private final ImageLayer died;
    private final ImageLayer reset;
    private final ImageLayer home;
    private Number nb = new Number(350f,50f);
    private Number1 nb1 = new Number1(280f,50f);
    public static int total;
    private final TestScreen ts;
    public static int totalcoin=0;
    public static int totalstar=0;
    public static int totaldestroy=0;
    public static int totalfood=0;
    public static int life=3;
    private Fish f;

    public GameOver(final ScreenStack ss) {
        this.ss = ss;
        this.ts = new TestScreen(ss);
        total=totalcoin+totalstar+totaldestroy+totalfood;
        Number.total=total;
        Number.totalcoin=totalcoin;
        Number.totalstar=totalstar;
        Number.totaldestroy=totaldestroy;
        Number.totalfood=totalfood;

        Image bgImage = assets().getImage("images/bg.png");
        this.bg = graphics().createImageLayer(bgImage);

        /*Image diedImage = assets().getImage("images/died.png");
        this.died = graphics().createImageLayer(diedImage);
        died.setTranslation(50, -100);*/

        Image resetImage = assets().getImage("images/resetb.png");
        this.reset = graphics().createImageLayer(resetImage);
        reset.setTranslation(210, 245);

        Image hImage = assets().getImage("images/homeb.png");
        this.home = graphics().createImageLayer(hImage);
        home.setTranslation(330, 247);


        reset.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(ts); // push screen
                HomeScreen.soundClick.play();
                totalcoin=0;totalstar=0;totaldestroy=0;totalfood=0;life=3;
            }
        });
        home.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(new HomeScreen(ss)); // push screen
                HomeScreen.soundClick.play();
                totalcoin=0;totalstar=0;totaldestroy=0;totalfood=0;
            }
        });

    }



    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);
        //this.layer.add(died);
        this.layer.add(reset);
        this.layer.add(home);
        this.layer.add(nb.layer());
        this.layer.add(nb1.layer());
        viewScored();
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        nb.update(delta);nb1.update(delta);
    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        nb.paint(clock);nb1.paint(clock);
    }

    public void viewScored(){
        if(total==0){
            nb1.state = Number1.State.NUMBER0;
            nb.state = Number.State.NUMBER0;
        }if(total==1){
            nb1.state = Number1.State.NUMBER0;
            nb.state = Number.State.NUMBER1;
        }if(total==2){
            nb1.state = Number1.State.NUMBER0;
            nb.state = Number.State.NUMBER2;
        }if(total==3){
            nb1.state = Number1.State.NUMBER0;
            nb.state = Number.State.NUMBER3;
        }if(total==4){
            nb1.state = Number1.State.NUMBER0;
            nb.state = Number.State.NUMBER4;
        }if(total==5){
            nb1.state = Number1.State.NUMBER0;
            nb.state = Number.State.NUMBER5;
        }if(total==6){
            nb1.state = Number1.State.NUMBER0;
            nb.state = Number.State.NUMBER6;
        }if(total==7){
            nb1.state = Number1.State.NUMBER0;
            nb.state = Number.State.NUMBER7;
        }if(total==8){
            nb1.state = Number1.State.NUMBER0;
            nb.state = Number.State.NUMBER8;
        }if(total==9){
            nb1.state = Number1.State.NUMBER0;
            nb.state = Number.State.NUMBER9;
        }if(total==10){
            nb1.state = Number1.State.NUMBER0;
            nb.state = Number.State.NUMBER1;
        }if(total==11){
            nb1.state = Number1.State.NUMBER1;
            nb.state = Number.State.NUMBER1;
        }if(total==12){
            nb1.state = Number1.State.NUMBER1;
            nb.state = Number.State.NUMBER2;
        }if(total==13){
            nb1.state = Number1.State.NUMBER1;
            nb.state = Number.State.NUMBER3;
        }if(total==14){
            nb1.state = Number1.State.NUMBER1;
            nb.state = Number.State.NUMBER4;
        }if(total==15){
            nb1.state = Number1.State.NUMBER1;
            nb.state = Number.State.NUMBER5;
        }if(total==16){
            nb1.state = Number1.State.NUMBER1;
            nb.state = Number.State.NUMBER6;
        }if(total==17){
            nb1.state = Number1.State.NUMBER1;
            nb.state = Number.State.NUMBER7;
        }if(total==18){
            nb1.state = Number1.State.NUMBER1;
            nb.state = Number.State.NUMBER8;
        }if(total==19){
            nb1.state = Number1.State.NUMBER1;
            nb.state = Number.State.NUMBER9;
        }if(total==20){
            nb1.state = Number1.State.NUMBER0;
            nb.state = Number.State.NUMBER2;
        }if(total==21){
            nb1.state = Number1.State.NUMBER2;
            nb.state = Number.State.NUMBER1;
        }if(total==22){
            nb1.state = Number1.State.NUMBER2;
            nb.state = Number.State.NUMBER2;
        }if(total==23){
            nb1.state = Number1.State.NUMBER2;
            nb.state = Number.State.NUMBER3;
        }if(total==24){
            nb1.state = Number1.State.NUMBER2;
            nb.state = Number.State.NUMBER4;
        }if(total==25){
            nb1.state = Number1.State.NUMBER2;
            nb.state = Number.State.NUMBER5;
        }if(total==26){
            nb1.state = Number1.State.NUMBER2;
            nb.state = Number.State.NUMBER6;
        }if(total==27){
            nb1.state = Number1.State.NUMBER2;
            nb.state = Number.State.NUMBER7;
        }if(total==28){
            nb1.state = Number1.State.NUMBER2;
            nb.state = Number.State.NUMBER8;
        }if(total==29){
            nb1.state = Number1.State.NUMBER2;
            nb.state = Number.State.NUMBER9;
        }if(total==30){
            nb1.state = Number1.State.NUMBER3;
            nb.state = Number.State.NUMBER0;
        }if(total==31){
            nb1.state = Number1.State.NUMBER3;
            nb.state = Number.State.NUMBER1;
        }if(total==32){
            nb1.state = Number1.State.NUMBER3;
            nb.state = Number.State.NUMBER2;
        }if(total==33){
            nb1.state = Number1.State.NUMBER3;
            nb.state = Number.State.NUMBER3;
        }if(total==34){
            nb1.state = Number1.State.NUMBER3;
            nb.state = Number.State.NUMBER4;
        }if(total==35){
            nb1.state = Number1.State.NUMBER3;
            nb.state = Number.State.NUMBER5;
        }if(total==36){
            nb1.state = Number1.State.NUMBER3;
            nb.state = Number.State.NUMBER6;
        }if(total==37){
            nb1.state = Number1.State.NUMBER3;
            nb.state = Number.State.NUMBER7;
        }if(total==38){
            nb1.state = Number1.State.NUMBER3;
            nb.state = Number.State.NUMBER8;
        }if(total==39){
            nb1.state = Number1.State.NUMBER3;
            nb.state = Number.State.NUMBER9;
        }if(total==40){
            nb1.state = Number1.State.NUMBER4;
            nb.state = Number.State.NUMBER0;
        }if(total==41){
            nb1.state = Number1.State.NUMBER4;
            nb.state = Number.State.NUMBER1;
        }if(total==42){
            nb1.state = Number1.State.NUMBER4;
            nb.state = Number.State.NUMBER2;
        }if(total==43){
            nb1.state = Number1.State.NUMBER4;
            nb.state = Number.State.NUMBER3;
        }if(total==44){
            nb1.state = Number1.State.NUMBER4;
            nb.state = Number.State.NUMBER4;
        }if(total==45){
            nb1.state = Number1.State.NUMBER4;
            nb.state = Number.State.NUMBER5;
        }if(total==46){
            nb1.state = Number1.State.NUMBER4;
            nb.state = Number.State.NUMBER6;
        }if(total==47){
            nb1.state = Number1.State.NUMBER4;
            nb.state = Number.State.NUMBER7;
        }if(total==48){
            nb1.state = Number1.State.NUMBER4;
            nb.state = Number.State.NUMBER8;
        }if(total==49){
            nb1.state = Number1.State.NUMBER4;
            nb.state = Number.State.NUMBER9;
        }if(total==50){
            nb1.state = Number1.State.NUMBER4;
            nb.state = Number.State.NUMBER0;
        }
    }
}