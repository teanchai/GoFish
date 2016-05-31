package sut.game01.core;

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
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import static playn.core.PlayN.*;

import java.util.HashMap;

public class TestScreen extends Screen{

    public static HashMap bodies = new HashMap<Body,String>();
    public static String debugString = String.valueOf(bodies);
    public static int ii = 0;
    private final ScreenStack ss;
    private final ImageLayer bg;
    //private final ImageLayer backButton;
    public static float M_PER_PIXEL = 1 / 26.666667f;
    //size of world
    private static int width = 24;
    private static int height = 18;
    private World world;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugdraw;
    //private Fish f = new Fish(100f,200f, world);
    int i=0;
    int j=0;
    int c =0;
    private Fish f ;
    private Coin coin ;
    public static int aa = 0;
    private Star star;
    private int clash=0;
    private Bigfish bf;
    private Handicap hp;
    private int clash1=0;


    public TestScreen(final ScreenStack ss) {
        this.ss = ss;
        Vec2 gravity = new Vec2(0.0f,40.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);
        Image bgImage = assets().getImage("images/bg.png");
        this.bg = graphics().createImageLayer(bgImage);


        /*

        backButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top()); // pop screen
            }
        });*/
    }
    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);

        hp = new Handicap(world,300f,314f);
        f = new Fish(50f, 200f, world);
        //coin = new Coin(world,500f,100f);
        star = new Star(world,300f,100f);

        this.layer.add(hp.layer());
        this.layer.add(f.layer());
//        this.layer.add(coin.layer());
        this.layer.add(star.layer());
        /*mouse().setListener(new Mouse.Adapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                f.add(i, new Fish(event.x(),event.y(),world));
                layer.add(f.get(i).layer());
                i++;
                j++;

            }
        });*/
        if(showDebugDraw) {
            CanvasImage image = graphics().createImage( (int)(width/ TestScreen.M_PER_PIXEL),(int)(height / TestScreen.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugdraw = new DebugDrawBox2D();
            debugdraw.setCanvas(image);
            debugdraw.setFlipY(false);
            debugdraw.setStrokeAlpha(150);
            debugdraw.setFillAlpha(75);
            debugdraw.setStrokeWidth(2.0f);
            debugdraw.setFlags( DebugDraw.e_shapeBit | DebugDraw.e_jointBit /*| DebugDraw.e_aabbBit*/);
            debugdraw.setCamera(0,0,1f/TestScreen.M_PER_PIXEL);
            world.setDebugDraw(debugdraw);
        }

        final Body ground = world.createBody(new BodyDef());
        final EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0,15),new Vec2(width,15));
        ground.createFixture(groundShape,0.0f);

        final Body ground1 = world.createBody(new BodyDef());
        EdgeShape groundShape1 = new EdgeShape();
        groundShape1.set(new Vec2(width,0),new Vec2(width,height));
        ground1.createFixture(groundShape1, 0f);

        final Body ground2 = world.createBody(new BodyDef());
        EdgeShape groundShape2 = new EdgeShape();
        groundShape2.set(new Vec2(0,height),new Vec2(0,0));
        ground2.createFixture(groundShape2, 0f);

        final Body ground3 = world.createBody(new BodyDef());
        EdgeShape groundShape3 = new EdgeShape();
        groundShape3.set(new Vec2(0,0),new Vec2(width,0));
        ground3.createFixture(groundShape3, 0f);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                /*if(a == coin.body()||b == coin.body()){
                    f.state = Fish.State.ATTK;
                    c++;
                }*/
                if(a == ground||a == ground1 ||a == ground2 ||a == ground3){
                    f.state = Fish.State.IDLE;
                }
                if(a == star.body()||b == star.body()){
                    clash=1;
                    f.state = Fish.State.ATTK;

                }
                if(a==hp.body()||b==hp.body()){
                    clash1++;
                    f.state = Fish.State.ATTK;

                }
                /*if(bodies.get(a) != null){
                    debugString = bodies.get(a) + " contacted with " + bodies.get(b);

                    //b.applyForce(new Vec2(100f, 0), b.getPosition());
                    //b.applyLinearImpulse(new Vec2(10f,-10f),b.getPosition());
                }*/
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
    @Override
    public void update(int delta) {
        super.update(delta);
        //.update(delta);
        world.step(0.033f,10,10);
        hp.update(delta);
        star.update(delta);
        f.update(delta);
        // System.out.println(j);

        //coin.update(delta);
        // System.out.println(j);



        /*if(clash>0){bf.update(delta);}*/

    }
    public void paint(Clock clock) {
         super.paint(clock);
         //coin.paint(clock);
         hp.paint(clock);
         star.paint(clock);
         f.paint(clock);
        if (showDebugDraw) {
            debugdraw.getCanvas().clear();
            world.drawDebugData();
            //show point
            debugdraw.getCanvas().drawText(String.valueOf(c), 620f, 300f);
            debugdraw.getCanvas().setFillColor(Color.rgb(200,200,200));
            if(clash>0) {
                star.layer().destroy();
                world.destroyBody(star.body());
                //f.layer().destroy();
                //world.destroyBody(f.body());
             /*bf = new Bigfish(100f,300f,world);
             this.layer.add(bf.layer());*/
                //bf.paint(clock);
            }
            /*if(c>0){
                coin.layer().destroy();
                world.destroyBody(coin.body());
            }*/
            if(clash1==3){
                f.layer().destroy();
                world.destroyBody(f.body());

                ss.push(new GameOver(ss));
            }
            f.paint(clock);

        }
    }



}

