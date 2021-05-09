package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.AnimationGameBitmapVertical;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.StaticGameBitmap;

public class TowerBuilder implements GameObject {
    private final AnimationGameBitmapVertical buttonBitmap
            = new AnimationGameBitmapVertical(R.mipmap.gui_tower, 0, 10);
    private final StaticGameBitmap guiBitmap;
    private final StaticGameBitmap slotBitmap;

    private final float x;
    private final float y;
    private boolean isOn = false;
    private int checkedButton = 0;

    public TowerBuilder(float x, float y) {
        this.x = x;
        this.y = y;
        guiBitmap = new StaticGameBitmap(R.mipmap.gui_common, 2);
        slotBitmap = new StaticGameBitmap(R.mipmap.tower_slot, 2);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        slotBitmap.draw(canvas, x, y);
        if (isOn) {
            guiBitmap.draw(canvas, x, y);
            buttonBitmap.draw(canvas, x - 150, y - 150, 0);
            buttonBitmap.draw(canvas, x + 150, y - 150, 2);
            buttonBitmap.draw(canvas, x - 150, y + 150, 4);
            buttonBitmap.draw(canvas, x + 150, y + 150, 6);
        }
    }

    public void pressOn(float x, float y, boolean down) {
        if (down) {
            isOn = IsIn(x, y);
        }
    }

    private boolean IsIn(float x, float y) {
        int hw = slotBitmap.getWidth() / 2;
        int hh = slotBitmap.getHeight() / 2;
        if ( (this.x - hw) < x && x < (this.x + hw)) {
            if ( (this.y - hh) < y && y < (this.y + hh)) {
                return true;
            }
        }
        return false;
    }
}
