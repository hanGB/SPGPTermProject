package kr.ac.kpu.s2018182039.kingdomrush.game.objects.buller;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.BoxCollidable;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.Recyclable;
import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.StaticGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.MainGameState;

public class BombBullet implements GameObject, BoxCollidable, Recyclable {
    private static final float GRAVITY = 4.8f;
    private float x;
    private StaticGameBitmap bitmap;
    private float y;
    private int speed;
    private RectF boundingRect = new RectF();
    private float dx;
    private float dy;

    private BombBullet(int resId, float x, float y, float dx, float dy, int speed) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;

        this.bitmap = new StaticGameBitmap(resId, 4);
    }

    public static BombBullet get(int resId, float x, float y, float dx, float dy, int speed) {
        MainGameState state = MainGameState.get();
        BombBullet bullet = (BombBullet)state.get(BombBullet.class);
        if (bullet == null) {
            return new BombBullet(resId, x, y, dx, dy, speed);
        }
        bullet.init(resId, x, y, dx, dy, speed);
        return bullet;
    }

    private void init(int resId, float x, float y, float dx, float dy, int speed) {
        this.bitmap.changeImage(resId);
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
    }

    @Override
    public void update() {
        MainGameState state = MainGameState.get();
        x += speed * dx * state.frameTime;
        y += speed * dy * state.frameTime;

        dy += GRAVITY * state.frameTime;

        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();
        boolean toBeDeleted = false;

        if (x < 0 || x > w) {
            toBeDeleted = true;
        }
        if (y < 0 || y > h) {
            toBeDeleted = true;
        }

        if (toBeDeleted) {
            state.remove(this, true);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        float radians = (float)Math.atan2((double)dy, (double)dx);
        canvas.rotate((float)Math.toDegrees(radians), x, y);
        bitmap.draw(canvas, x, y);
        canvas.restore();
    }

    @Override
    public RectF getBoundingRect() {
        bitmap.getBoundingRect(x, y, boundingRect);
        return boundingRect;
    }

    @Override
    public void recycle() {
        // 재활용통에 들어가는 시점에 해야하는 일을 하는 함수
    }
}
