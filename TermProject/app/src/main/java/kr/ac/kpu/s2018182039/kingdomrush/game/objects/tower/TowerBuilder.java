package kr.ac.kpu.s2018182039.kingdomrush.game.objects.tower;

import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.AnimationGameBitmapVertical;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.StaticGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainScene;

public class TowerBuilder implements GameObject {
    private final AnimationGameBitmapVertical buttonBitmap
            = new AnimationGameBitmapVertical(R.mipmap.gui_tower, 0, 10, 2);
    private final StaticGameBitmap guiBitmap;
    private final StaticGameBitmap slotBitmap;

    private float x;
    private float y;
    private boolean isOn = false;

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
            if (isOn) {
                BaseGame game = BaseGame.get();
                if (IsInButton(x, y, -150, -150)){
                    game.add(MainScene.Layer.tower.ordinal(), new ArcherTower(this.x, this.y));
                    game.remove(this, true);
                } else if (IsInButton(x, y, 150, -150)) {
                    game.add(MainScene.Layer.tower.ordinal(), new SoldierTower(this.x, this.y));
                    game.remove(this, true);
                } else if (IsInButton(x, y, -150, 150)) {
                    game.add(MainScene.Layer.tower.ordinal(), new MagicTower(this.x, this.y));
                    game.remove(this, true);
                } else if (IsInButton(x, y, 150, 150)) {
                    game.add(MainScene.Layer.tower.ordinal(), new BombTower(this.x, this.y));
                    game.remove(this, true);
                }
            }
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

    private boolean IsInButton(float x, float y, float locationX, float locationY) {
        int hw = buttonBitmap.getWidth() / 2;
        int hh = buttonBitmap.getHeight() / 2;
        if ( (this.x + locationX- hw) < x && x < (this.x + locationX + hw)) {
            if ( (this.y + locationY- hh) < y && y < (this.y + locationY+ hh)) {
                return true;
            }
        }
        return false;
    }

    public void adjustLocationWithBackground(float x, float y) {
        this.x -= x;
        this.y -= y;
    }
}
