package sut.game01.core.Screen;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.Clock;
import sut.game01.core.character.*;
import sut.game01.core.items.Food;
import sut.game01.core.items.Star;
import sut.game01.core.pipe.Pipenorth;
import sut.game01.core.pipe.Pipesouth;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import static playn.core.PlayN.*;
import java.util.ArrayList;
import java.util.Random;

public class TestScreen extends Screen{

    private final ScreenStack ss;
    private final ImageLayer bg;
    private final ImageLayer foreground;
    public static float M_PER_PIXEL = 1 / 26.666667f;
    //size of world
    private static int width = 24;
    private static int height = 18;
    private final ImageLayer heart1;
    private final ImageLayer heart2;
    private final ImageLayer heart;
    private Sound soundHit;
    private Sound soundJump;
    private Sound soundPowerup;
    private Sound soundStar;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugdraw;
    public int i=0;
    public int j=0;
    public int c =0;
    private Fish f ;
    public int clash=0,clash1=0,clashfood=0;
    public int destroyed=0,destroyed1=0;
    private Random rand = new Random();
    private ArrayList<Pipenorth> pipenorths = new ArrayList<Pipenorth>();
    private ArrayList<Pipesouth> pipesouths = new ArrayList<Pipesouth>();
    //private ArrayList<Coin> coins = new ArrayList<Coin>();
    private ArrayList<Food> foods = new ArrayList<Food>();
    private ArrayList<Star> stars = new ArrayList<Star>();
    public static float widthfixed = 600f;
    private boolean gameover = false;
    private Sound soundCoin;
    private Sound soundGameover;
    Random random = new Random();
    Vec2 gravity = new Vec2(10.0f,50.0f);
    private World world=new World(gravity);
    float widthfix = widthfixed-random.nextInt(350);
    final Body ground = world.createBody(new BodyDef());
    final Body ground1 = world.createBody(new BodyDef());
    final Body ground2 = world.createBody(new BodyDef());
    final Body ground3 = world.createBody(new BodyDef());


    public TestScreen(final ScreenStack ss) {

        this.ss = ss;
        soundCoin = assets().getSound("sounds/coin");
        soundHit = assets().getSound("sounds/hurt");
        soundJump = assets().getSound("sounds/jump");
        soundPowerup = assets().getSound("sounds/big");
        soundStar = assets().getSound("sounds/pass");
        soundGameover = assets().getSound("sounds/gameover");


        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        Image bgImage = assets().getImage("images/bg2.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image heartImage = assets().getImage("images/heart.png");
        heart = graphics().createImageLayer(heartImage);
        heart1 = graphics().createImageLayer(heartImage);
        heart2 = graphics().createImageLayer(heartImage);

        Image fgImage = assets().getImage("images/seaground.png");
        this.foreground = graphics().createImageLayer(fgImage);


        PlayN.keyboard().setListener(new Keyboard.Adapter(){
            @Override
            public void onKeyDown(Keyboard.Event event) {
                try {
                    if(event.key() == Key.SPACE & f.body().isActive()){
                        super.onKeyDown(event);
                        jump();
                    }if(event.key() == Key.ESCAPE){
                        //f.layer().destroy();
                        world.destroyBody(f.body());
                        pipenorths.clear();
                        pipesouths.clear();
                        foods.clear();
                        //coins.clear();
                        stars.clear();
                        ss.push(new HomeScreen(ss));
                    }if(event.key() == Key.ENTER){
                        ss.push(new TestScreen(ss));
                    }
                }
                catch (NullPointerException ex) {
                    ex.printStackTrace();
                }



            }
        });
        /*PlayN.mouse().setListener(new Mouse.Adapter(){
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                super.onMouseDown(event);
                f.body().applyForce(new Vec2(0, -4000f), f.body().getPosition());
                soundJump.play();
            }
        });*/
    }

    private void jump() {
        f.body().applyForce(new Vec2(0, -4000f), f.body().getPosition());
        soundJump.play();
    }

    @Override
    public void wasShown() {
        super.wasShown();
        float heightfixed = 480f;
        float heightrand = heightfixed - rand.nextInt(150);
        float pnrandheight =  heightrand + 50f;
        float psrandheight = heightrand - 480f;
        this.layer.add(bg);
        heart.setTranslation(0f,0f);
        heart1.setTranslation(40f,0f);
        heart2.setTranslation(80f,0f);
        showLife();
        if(GameOver.life==3){
            this.layer.add(heart);
            this.layer.add(heart1);
            this.layer.add(heart2);
        }if(GameOver.life==2){
            this.layer.add(heart);
            this.layer.add(heart1);
        }if(GameOver.life==1){
            this.layer.add(heart);
        }

        f = new Fish(10f, 300f, world);
        //coins.add(new Coin(world,600f,heightrand-200f)) ;

        foods.add(new Food(world,widthfixed+20f-new Random().nextInt(640)-200f,heightfixed-rand.nextInt(440)));

        /*pn = new Pipenorth(300f,heightfixed - randheight + 50f ,world);
        ps = new Pipesouth(300f,heightfixed - randheight - 480f,world);*/
        pipenorths.add(new Pipenorth(widthfix,pnrandheight, world));
        pipesouths.add(new Pipesouth(widthfix,psrandheight, world));

        this.layer.add(pipenorths.get(i).layer());
        this.layer.add(pipesouths.get(i).layer());
        //this.layer.add(coins.get(i).layer());

        stars.add(new Star(world,800-new Random().nextInt(640),400-new Random().nextInt(800)));
        this.layer.add(stars.get(i).layer());

        this.layer.add(foods.get(i).layer());
        foreground.setTranslation(0f,385f);
        this.layer.add(foreground);
        this.layer.add(f.layer());

        if(showDebugDraw) {
            CanvasImage image = graphics().createImage( (int)(width/ TestScreen.M_PER_PIXEL),(int)(height / TestScreen.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugdraw = new DebugDrawBox2D();
            debugdraw.setCanvas(image);
            debugdraw.setFlipY(false);
            debugdraw.setStrokeAlpha(150);
            debugdraw.setFillAlpha(75);
            debugdraw.setStrokeWidth(2.0f);
            debugdraw.setFlags( DebugDraw.e_shapeBit | DebugDraw.e_jointBit);
            debugdraw.setCamera(0,0,1f/TestScreen.M_PER_PIXEL);


            world.setDebugDraw(debugdraw);
        }

        final EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0,15),new Vec2(240,15));
        ground.createFixture(groundShape,0f);

        final EdgeShape groundShape1 = new EdgeShape();
        groundShape1.set(new Vec2(width+1,-5),new Vec2(width+1,height));
        ground1.createFixture(groundShape1, 0f);

        EdgeShape groundShape2 = new EdgeShape();
        groundShape2.set(new Vec2(0,height),new Vec2(0,0));
        ground2.createFixture(groundShape2, 0f);

        EdgeShape groundShape3 = new EdgeShape();
        groundShape3.set(new Vec2(0,-5),new Vec2(width,-5));
        ground3.createFixture(groundShape3, 0f);


        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if(contact.getFixtureA().getBody()== f.body()
                        ||contact.getFixtureB().getBody()== f.body()) {
                    Body a = contact.getFixtureA().getBody();
                    Body b = contact.getFixtureB().getBody();
                    setContacted(a,b);
                }

            }
            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });
    }

    public void setContacted(Body a,Body b) {
        /*if(f.body() == coins.get(i).body()||b == coins.get(i).body()){
                    soundCoin.play();
                    c++;
                    GameOver.totalcoin++;
                }*/
        if(a == ground1 || b == ground1){
            GameOver.totalcoin++;
            ss.push(new TestScreen(ss));
        }
        if(a == stars.get(i).body()||b == stars.get(i).body()){
            soundStar.play();
            clash=1;
            GameOver.totalstar++;
            try {
                pipenorths.get(i).body().setActive(false);
                pipesouths.get(i).body().setActive(false);
            }
            catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        if(a == foods.get(i).body()||b == foods.get(i). body()){
            soundPowerup.play();
            clashfood=1;
            f.state = Fish.State.BIG;
            GameOver.totalfood++;
        }
        if((a==pipenorths.get(i).body()||b==pipenorths.get(i).body())){
            soundHit.play();
            clash1++;
            f.state = Fish.State.ATTK;
            f.body().applyLinearImpulse(new Vec2(-50f, -50f), f.body().getPosition());


        }if((a==pipesouths.get(i).body()||b==pipesouths.get(i).body())){
            soundHit.play();
            clash1++;
            f.state = Fish.State.ATTK;
            f.body().applyLinearImpulse(new Vec2(-50f, 50f), f.body().getPosition());


        }if((a==pipenorths.get(i).body()||b==pipenorths.get(i).body())&&clashfood==1){
            clash1=0;
            destroyed++;
            f.state = Fish.State.ATTK;
            GameOver.totaldestroy++;

        }if((a==pipesouths.get(i).body()||b==pipesouths.get(i).body())&&clashfood==1){
            clash1=0;
            destroyed1++;
            f.state = Fish.State.ATTK;
            GameOver.totaldestroy++;
        }
    }

    public void showLife() {
        this.layer.add(heart);
        this.layer.add(heart1);
        this.layer.add(heart2);
    }
    @Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.022f,10,10);
        stars.get(i).update(delta);
        foods.get(i).update(delta);
        f.update(delta);
        pipenorths.get(i).update(delta);
        pipesouths.get(i).update(delta);
       // coins.get(i).update(delta);
    }
    public void paint(Clock clock) {
        super.paint(clock);
        // coins.get(i).paint(clock);
        stars.get(i).paint(clock);
        f.paint(clock);
        foods.get(i).paint(clock);
        pipenorths.get(i).paint(clock);
        pipesouths.get(i).paint(clock);
        showDebugDraw();
    }

    private void showDebugDraw(){
        if (showDebugDraw) {
            debugdraw.getCanvas().clear();
            //show point
            debugdraw.getCanvas().drawText("Coin = "+String.valueOf(c),10f,230f);
            debugdraw.getCanvas().drawText("Star = "+String.valueOf(clash),10f,245f);
            debugdraw.getCanvas().drawText("Destroyed Pipe = "+ String.valueOf(destroyed+destroyed1),10f,260f);
            world.drawDebugData();
            if(clash>0) {
                stars.get(i).layer().destroy();
                world.destroyBody(stars.get(i).body());
                System.out.println("Star = "+String.valueOf(clash));

            }
            if(clashfood==1){
                foods.get(i).layer().destroy();
                world.destroyBody(foods.get(i).body());
            }
            /*if(c>0){
                coins.get(i).layer().destroy();
                world.destroyBody(coins.get(i).body());
                System.out.println("Coin = " + String.valueOf(c));
            }*/
            if(clash1==1){
                heart2.destroy();
                GameOver.life--;
            }
            if(clash1==2){
                heart1.destroy();
                GameOver.life--;
            }
            if(clash1==3){
                heart.destroy();
                GameOver.life--;
                gameover=true;
            }

            if(destroyed>0){
                pipenorths.get(i).layer().destroy();
                world.destroyBody(pipenorths.get(i).body());
                clashfood=0;
            }
            if(destroyed1>0){
                pipesouths.get(i).layer().destroy();
                world.destroyBody(pipesouths.get(i).body());
                clashfood=0;
            }
            if(destroyed>0|destroyed1>0){
                System.out.println("Destroyed Pipe = "+ String.valueOf(destroyed+destroyed1));
            }

        }
        if(gameover){
            soundGameover.play();
            //f.layer().destroy();
            world.destroyBody(f.body());
            pipenorths.clear();
            pipesouths.clear();
            foods.clear();
            //coins.clear();
            stars.clear();
            HomeScreen.soundbg.stop();
            //f.body().setActive(false);
            ss.push(new GameOver(ss));

        }
    }



}

