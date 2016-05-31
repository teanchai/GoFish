package sut.game01.core.items;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.Screen.TestScreen;
import sut.game01.core.sprites.Sprite;
import sut.game01.core.sprites.SpriteLoader;

public class Food {

    private Sprite sprite;
    private  int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Body body;

    public enum State {
        IDLE
    };

    private State state = State.IDLE;


    public Food(final World world, final float x, final float y) {
        sprite = SpriteLoader.getSprite("images/Food.json");
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
        bodyDef.type = BodyType.STATIC;
        bodyDef.position = new Vec2(0,0);
        body = world.createBody(bodyDef);
        body.setGravityScale(0f);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.layer().width() * TestScreen.M_PER_PIXEL/2  ,
                sprite.layer().height()* TestScreen.M_PER_PIXEL /2 );
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

