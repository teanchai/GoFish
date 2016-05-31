package sut.game01.core;
import playn.core.*;
import playn.core.util.Clock;
import pythagoras.f.FloatMath;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.particle.Effector;
import tripleplay.particle.Emitter;
import tripleplay.particle.Generator;
import tripleplay.particle.Initializer;
import tripleplay.particle.Particles;
import tripleplay.particle.effect.Gravity;
import tripleplay.particle.init.Color;
import tripleplay.particle.init.Lifespan;
import tripleplay.particle.init.Transform;
import tripleplay.util.Randoms;
import static playn.core.PlayN.*;

import java.util.Random;

import static tripleplay.particle.ParticleBuffer.*;
public class Snow extends Screen{
    private final Particles _parts = new Particles();
    private final Randoms _rands = Randoms.with(new Random());
    private float _xVelScale = 1;
    private float _rotSin, _rotCos;
    private final ScreenStack ss;


    public Snow(final ScreenStack ss){
        this.ss = ss;
        // load up our snowflake image
        final Image flake = assets().getImage("images/snowflake.png");
        Emitter snower = _parts.createEmitter(200, flake);
        snower.layer.setDepth(1);
        snower.generator = Generator.constant(10);

        snower.initters.add(Lifespan.constant(10));
        snower.initters.add(Color.constant(0xFFFFFFFF));
        snower.initters.add(Transform.identity());

        // randomly initialize position along the top edge or the right edge of the screen
        snower.initters.add(new Initializer() {
            @Override public void init (int index, float[] data, int start) {
                if (_rands.getBoolean()) {
                    data[start + TX] = _rands.getFloat(graphics().width());
                    data[start + TY] = -flake.height();
                } else {
                    data[start + TX] = graphics().width() + flake.width();
                    data[start + TY] = _rands.getFloat(graphics().height());
                }
            }
        });
        // initialize x velocity with random variance, and y velocity to constant
        snower.initters.add(new Initializer() {
            @Override public void init (int index, float[] data, int start) {
                data[start + VEL_X] = _rands.getNormal(-15, 5);
                data[start + VEL_Y] = 50;
            }
        });

        // add a little gravity for fun
        snower.effectors.add(new Gravity(3));

        // add a moving effector that also scales the x velocity by a global sinusoid; this causes
        // all the flakes to quickly burst into rapid motion periodically (simultaneously)
        snower.effectors.add(new Effector() {
            @Override public void apply (int index, float[] data, int start, float now, float dt) {
                // apply a rotation
                multiply(data, start, _rotCos, _rotSin, -_rotSin, _rotCos, 0, 0);
                // and apply our velocity
                data[start + TX] += data[start + VEL_X] * dt * _xVelScale;
                data[start + TY] += data[start + VEL_Y] * dt;
            }
        });
    }
    @Override
    public void update(int delta) {
        super.update(delta);
    }
    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        _xVelScale = 1 + 10*Math.abs(FloatMath.sin(clock.time()));
        float dt = clock.dt() / 1000f;
        _rotSin = FloatMath.sin(dt);
        _rotCos = FloatMath.cos(dt);
        _parts.paint(clock);
    }
}
