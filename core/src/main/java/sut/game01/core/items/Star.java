package sut.game01.core.items;

import org.jbox2d.collision.shapes.CircleShape;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.Screen.TestScreen;
import sut.game01.core.sprites.Sprite;
import sut.game01.core.sprites.SpriteLoader;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class Star {

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


    public Star(final World world, final float x, final float y) {
        sprite = SpriteLoader.getSprite("images/star.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.layer().width() / 2f,
                        sprite.layer().height() /2f);
                sprite.layer().setTranslation(x, y);
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
        bodyDef.type = BodyType.STATIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);
        body.setGravityScale(0f);
        CircleShape shape = new CircleShape();
        shape.setRadius(0.9f);
        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.density= 0.0f;
        body.createFixture(fixture);
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
            spriteIndex = offset + ((spriteIndex + 1) % 8);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
    }
    public void paint(Clock clock) {
        if(hasLoaded == false)
            return;

        sprite.layer().setTranslation(
                (body.getPosition().x / TestScreen.M_PER_PIXEL),
                body.getPosition().y / TestScreen.M_PER_PIXEL

        );
    }
    public Layer layer() {
        return sprite.layer();
    }


}
