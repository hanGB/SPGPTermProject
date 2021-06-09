package kr.ac.kpu.s2018182039.kingdomrush.game.objects.buller;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.BoxCollidable;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.Recyclable;
import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.StaticGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.MainGameState;

public class Bullet implements GameObject, BoxCollidable, Recyclable {
    private float x;
    private StaticGameBitmap bitmap;
    private float y;
    private int speed;
    private RectF boundingRect = new RectF();
    private float dx;
    private float dy;
    public int damage;

    private Bullet(int resId, float x, float y, float dx, float dy, int speed, int damage) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;

        this.damage = damage;

        this.bitmap = new StaticGameBitmap(resId, 2);
    }

    public static Bullet get(int resId, float x, float y, float dx, float dy, int speed, int damage) {
        MainGameState state = MainGameState.get();
        Bullet bullet = (Bullet)state.get(Bullet.class);
        if (bullet == null) {
            return new Bullet(resId, x, y, dx, dy, speed, damage);
        }
        bullet.init(resId, x, y, dx, dy, speed, damage);
        return bullet;
    }

    private void init(int resId, float x, float y, float dx, float dy, int speed, int damage) {
        this.bitmap.changeImage(resId);
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
        this.damage = damage;
    }

    @Override
    public void update() {
        MainGameState state = MainGameState.get();
        x += speed * dx * state.frameTime;
        y += speed * dy * state.frameTime;

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
