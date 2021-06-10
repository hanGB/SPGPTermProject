package kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.StaticGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainScene;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.menu.StageMenuScene;

public class MovingButtonObject extends ButtonObject {
    private float dx, dy;
    private float distanceX;
    private float distanceY;
    private float speed = 2.f;

    public MovingButtonObject(int resId, int resIdPressed, float x, float y, float dx, float dy,
                              Rect rect, Rect rectPressed, int pixel_size) {

        super(resId, resIdPressed, x, y, rect, rectPressed, pixel_size);

        this.dx = dx;
        this.dy = dy;
        this.distanceX = dx - x;
        this.distanceY = dy - y;
    }

    @Override
    public void update() {
        BaseGame game = BaseGame.get();
        x += (distanceX * speed * game.frameTime);
        y += (distanceY * speed * game.frameTime);
        if ((distanceX > 0 && x > dx) || (distanceX < 0 && x < dx)) {
            x = dx;
        }
        if ((distanceY > 0 && y > dy) || (distanceY < 0 && y < dy)) {
            y = dy;
        }
    }
}
