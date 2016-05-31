package sut.game01.core.character;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.Screen.TestScreen;
import sut.game01.core.sprites.Sprite;
import sut.game01.core.sprites.SpriteLoader;

public class Bigfish  {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Body body;
    FixtureDef fixtureDef = new FixtureDef();

    public enum State {
        IDLE, RUN, ATTK
    }

    public State state = State.IDLE;

    private int e = 0;
    private int offset = 0;

    public Bigfish(final float x, final float y, final World world) {
        PlayN.keyboard().setListener(new Keyboard.Adapter(){
            @Override
            public void onKeyDown(Keyboard.Event event) {
                if(event.key() == Key.RIGHT ){
                    /*switch(state){
                        case IDLE : state = State.RUN;break;
                        case RUN : state = State.ATTK;break;
                        case ATTK: state = State.IDLE;break;

                    }*/
                    state = State.RUN;
                    body.applyForce(new Vec2(500f, -1000f), body.getPosition());
                }
                if(event.key() == Key.LEFT ){
                    state = State.RUN;
                    body.applyForce(new Vec2(-500f, -1000f), body.getPosition());
                }
            }
        });
        sprite = SpriteLoader.getSprite("images/star.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite    (spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x, y + 13f);
                body = initPhysicsBody(world,
                        TestScreen.M_PER_PIXEL *x,
                        TestScreen.M_PER_PIXEL *y);
                //coinn = initPhysicsBody1(world);
                hasLoaded = true;
            }
            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!", cause);
            }

        });
    }
    private Body initPhysicsBody(World world, float x, float y){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position =new Vec2(0,0);
        //  bodyDef.position = new Vec2(event.x() * M_PER_PIXEL, event.y() * M_PER_PIXEL);
        // bodyDef.active = new Boolean(true);
        body = world.createBody(bodyDef);
        /*TestScreen.bodies.put(body, "test_" + TestScreen.ii);
        TestScreen.ii++;*/
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.layer().width()*2 * TestScreen.M_PER_PIXEL/2 ,
                sprite.layer().height()*2* TestScreen.M_PER_PIXEL /2);
        // CircleShape shape = new CircleShape();
        // shape.setRadius(0.5f);
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.2f; // ทำให้เด้ง
        body.createFixture(fixtureDef);
        body.setLinearDamping(1f);//
        body.setTransform(new Vec2(x,y), 0f);
        return body;
    }

    public Layer layer() {
        return sprite.layer();

    }
    public Body body() {
        return body;
    }
    public void update(int delta){
        if (hasLoaded == false ){ return;}

        e += delta;
        if (e > 150) {
            switch(state){
                case IDLE : offset = 0;
                    break;
                case RUN : offset = 4;
                    break;
                case ATTK: offset = 8;
                    if(spriteIndex==11){
                        state = State.IDLE;
                    }break;
            }
            spriteIndex = offset + ((spriteIndex +1) % 4);
            sprite.setSprite(spriteIndex);
            e=0;
        }
    }
    public void paint(Clock clock) {
        if(hasLoaded == false)return;
        sprite.layer().setTranslation(
                (body.getPosition().x / TestScreen.M_PER_PIXEL) - 10,
                body.getPosition().y / TestScreen.M_PER_PIXEL);


    }


}

