package sut.game01.core.character;

import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.Screen.TestScreen;
import sut.game01.core.sprites.Sprite;
import sut.game01.core.sprites.SpriteLoader;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;


public class Fish  {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Body body;
    BodyDef bodyDef = new BodyDef();
    FixtureDef fixtureDef = new FixtureDef();
    public PolygonShape shape = new PolygonShape();

    public enum State {
        IDLE, RUN, ATTK , BIG
    }
    public State state = State.RUN;

    private int e = 0;
    private int offset = 0;

    public Fish(final float x, final float y, final World world) {

        sprite = SpriteLoader.getSprite("images/fish.json");

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
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position =new Vec2(0,0);
        body = world.createBody(bodyDef);
        Vec2 vec = new Vec2(0,0f);
        body.setGravityScale(1f);


        shape.setAsBox(sprite.layer().width() * TestScreen.M_PER_PIXEL / 2,
                    sprite.layer().height() * TestScreen.M_PER_PIXEL / 2);

        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0.2f; // ทำให้เด้ง
        body.createFixture(fixtureDef);
        body.setLinearDamping(1f);
        body.setTransform(new Vec2(x,y), 0f);
        body.setFixedRotation(true);
        body.setActive(true);
        return body;
    }


    public Body body() {
        return body;
    }
    public void update(int delta){
        if (hasLoaded == false ){ return;}

        e += delta;
        if (e > 180) {
            switch(state){
                case IDLE : offset = 0;
                    break;
                case RUN : offset = 4;
                    break;
                case ATTK: offset = 8;
                    if(spriteIndex==11){
                        state = State.RUN;
                    }break;
                case BIG: offset=12;

                    break;

            }
            spriteIndex = offset + ((spriteIndex +1) % 4);
            sprite.setSprite(spriteIndex);
            e=0;
        }
    }

    public void paint(Clock clock) {

        if(hasLoaded == false){return;}

        sprite.layer().setTranslation(
                (body.getPosition().x / TestScreen.M_PER_PIXEL),
                body.getPosition().y / TestScreen.M_PER_PIXEL);


    }
    public Layer layer() {
        return sprite.layer();

    }


}
