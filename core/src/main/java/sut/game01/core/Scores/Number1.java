package sut.game01.core.Scores;

import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.sprites.Sprite;
import sut.game01.core.sprites.SpriteLoader;

public class Number1  {
    public static int count;
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;

    public static int total;

    public static int totalcoin=0;
    public static int totalstar=0;
    public static int totaldestroy=0;
    public static int totalfood=0;

    public enum State {
        NUMBER0,NUMBER1,NUMBER2,NUMBER3,NUMBER4,NUMBER5,NUMBER6,NUMBER7,NUMBER8,NUMBER9

    }
    public State state = State.NUMBER0;

    private int e = 0;
    public static int offset = 0;

    public Number1(final float x, final float y) {
        sprite = SpriteLoader.getSprite("images/numbers.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite    (spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x, y + 13f);

                hasLoaded = true;
            }
            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!", cause);
            }

        });



    }


    public void update(int delta){
        if (hasLoaded == false ){ return;}

        e += delta;
        if (e > 180) {
            switch(state){
                case NUMBER0 : offset = 0;break;
                case NUMBER1 : offset = 1;break;
                case NUMBER2 : offset = 2;break;
                case NUMBER3 : offset = 3;break;
                case NUMBER4 : offset = 4;break;
                case NUMBER5 : offset = 5;break;
                case NUMBER6 : offset = 6;break;
                case NUMBER7 : offset = 7;break;
                case NUMBER8 : offset = 8;break;
                case NUMBER9 : offset = 9;break;

            }
            spriteIndex = offset + ((spriteIndex +1) % 1);
            sprite.setSprite(spriteIndex);
            e=0;
        }
    }

    public void paint(Clock clock) {
        if(hasLoaded == false){return;}
    }
    public Layer layer() {
        return sprite.layer();

    }


}


