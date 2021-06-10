package kr.ac.kpu.s2018182039.kingdomrush.game.objects.tower;

import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.AnimationGameBitmapVertical;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.StaticGameBitmap;

public class TowerObject implements GameObject {
    private final StaticGameBitmap towerBitmap;
    private final AnimationGameBitmapVertical buttonBitmap
            = new AnimationGameBitmapVertical(R.mipmap.gui_tower, 0, 10, 2);
    private final StaticGameBitmap guiBitmap;
    private final AnimationGameBitmapVertical animationTowerBitmap;
    private boolean isOn = false;

    protected float x;
    protected float y;
    protected int frameIndex;

    private boolean animationTower = false;

    TowerObject(float x, float y, int resId) {
        this.x = x;
        this.y = y;
        towerBitmap = new StaticGameBitmap(resId, 4);
        animationTowerBitmap = null;

        guiBitmap = new StaticGameBitmap(R.mipmap.gui_common, 2);
        isOn = false;
    }

    TowerObject(float x, float y) {
        towerBitmap = null;
        animationTowerBitmap = new AnimationGameBitmapVertical(R.mipmap.soldier_tower, 0, 2, 4);
        this.animationTower = true;
        this.x = x;
        this.y = y;
        guiBitmap = new StaticGameBitmap(R.mipmap.gui_common, 2);
        isOn = false;
    }


    @Override
    public void update() {

    }


    @Override
    public void draw(Canvas canvas) {
        if (animationTower) {
            animationTowerBitmap.draw(canvas, x, y, frameIndex);
        }
        else {
            towerBitmap.draw(canvas, x, y);
        }
        if (isOn) {
            guiBitmap.draw(canvas, x, y);
            buttonBitmap.draw(canvas, x, y - 200, 8);
        }
    }

    public void pressOn(float x, float y, boolean down) {
        if (down) {
            if (isOn) {
                BaseGame game = BaseGame.get();
                if (IsInButton(x, y, 0, -200)) {
                    upgrade();
                }
            }
            isOn = IsIn(x, y);
        }
    }

    private void upgrade() {
        isOn = false;
    }

    private boolean IsIn(float x, float y) {
        int hw;
        int hh;
        if (animationTower) {
            hw = animationTowerBitmap.getWidth() / 2;
            hh = animationTowerBitmap.getHeight() / 2;
        } else{
            hw = towerBitmap.getWidth() / 2;
            hh = towerBitmap.getHeight() / 2;
        }
        if ( (this.x - hw) < x && x < (this.x + hw)) {
            return (this.y - hh) < y && y < (this.y + hh);
        }
        return false;
    }

    private boolean IsInButton(float x, float y, float locationX, float locationY) {
        int hw = buttonBitmap.getWidth() / 2;
        int hh = buttonBitmap.getHeight() / 2;
        if ( (this.x + locationX- hw) < x && x < (this.x + locationX + hw)) {
            return (this.y + locationY - hh) < y && y < (this.y + locationY + hh);
        }
        return false;
    }

    public void adjustLocationWithBackground(float x, float y) {
        this.x -= x;
        this.y -= y;
    }
}
