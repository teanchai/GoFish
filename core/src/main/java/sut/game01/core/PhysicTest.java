package sut.game01.core;


import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import static playn.core.PlayN.*;

public class PhysicTest extends Screen {
    private final ScreenStack ss;
    public static float M_PER_PIXEL = 1 / 26.666667f;
    //size of world
    private static int width = 24;
    private static int height = 18;
    private World world;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugdraw;
    public PhysicTest (final ScreenStack ss){
        this.ss = ss;

        Vec2 gravity = new Vec2(0.0f,10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);
        //พื้นโลก


    }

    @Override
    public void wasShown() {
        super.wasShown();
        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0,height),new Vec2(width,height));
        ground.createFixture(groundShape,0.0f);

        if(showDebugDraw) {
            CanvasImage image = graphics().createImage( (int)(width/ PhysicTest.M_PER_PIXEL),(int)(height / PhysicTest.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugdraw = new DebugDrawBox2D();
            debugdraw.setCanvas(image);
            debugdraw.setFlipY(false);
            debugdraw.setStrokeAlpha(150);
            debugdraw.setFillAlpha(75);
            debugdraw.setStrokeWidth(2.0f);
            debugdraw.setFlags( DebugDraw.e_shapeBit | DebugDraw.e_jointBit /*| DebugDraw.e_aabbBit*/);
            debugdraw.setCamera(0,0,1f/PhysicTest.M_PER_PIXEL);
            world.setDebugDraw(debugdraw);
        }
        mouse().setListener(new Mouse.Adapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                super.onMouseDown(event);
                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyType.DYNAMIC;
                bodyDef.position = new Vec2(event.x()*M_PER_PIXEL,event.y()*M_PER_PIXEL);
                bodyDef.active = new Boolean(true);
                Body body = world.createBody(bodyDef);

                CircleShape shape = new CircleShape();
                shape.setRadius(0.4f);
                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.density = 0.4f;
                fixtureDef.friction = 0.1f;
                fixtureDef.restitution = 0.35f;
                body.createFixture(fixtureDef);
                body.setLinearDamping(0.2f);

            }
        });

    }

    @Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.033f,10,10);

    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        if(showDebugDraw){
            debugdraw.getCanvas().clear();
            world.drawDebugData();
        }
    }
}
