package sut.game01.core.character;
import org.jbox2d.collision.shapes.PolygonShape;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.Screen.TestScreen;
import sut.game01.core.sprites.Sprite;
import sut.game01.core.sprites.SpriteLoader;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class Handicap {

    private Sprite sprite;
    private  int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Body body;


    public enum State {
        IDLE
    };

    private State state = State.IDLE;

    private int e = 0;
    private int offset = 0;


    public Handicap(final World world, final float x, final float y) {
        sprite = SpriteLoader.getSprite("images/pole.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() /2f);
                sprite.layer().setTranslation(x, y+13f);
                body = initPhysicsBody(world,
                        TestScreen.M_PER_PIXEL * x,
                        TestScreen.M_PER_PIXEL * y);
                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error Loading Image!!",cause);
            }
        });
    }

    private Body initPhysicsBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.layer().width() * TestScreen.M_PER_PIXEL/2  ,
                sprite.layer().height()* TestScreen.M_PER_PIXEL /2 );
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 200.0f;
        //fixtureDef.friction = 0.1f;
        //fixtureDef.restitution = 1f;
        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y), 0f);
        return body;
    }
    public Body body() {
        return body;
    }

    public void update(int delta) {
        if (hasLoaded == false) return;
        e = e + delta;

        if (e > 180) {
            switch (state) {
                case IDLE: offset = 0; break;
            }
            spriteIndex = offset + ((spriteIndex + 1) % 1);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
    }
    public void paint(Clock clock) {
        if(hasLoaded == false){
            return;}

        sprite.layer().setTranslation(
                (body.getPosition().x / TestScreen.M_PER_PIXEL),
                body.getPosition().y / TestScreen.M_PER_PIXEL
        );
    }

    public Layer layer() {
        return sprite.layer();
    }



}
