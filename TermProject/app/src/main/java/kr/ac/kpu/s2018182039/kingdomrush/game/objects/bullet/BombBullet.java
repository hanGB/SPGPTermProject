package kr.ac.kpu.s2018182039.kingdomrush.game.objects.bullet;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.BoxCollidable;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.Recyclable;
import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.StaticGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;

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
        BaseGame game = BaseGame.get();
        BombBullet bullet = (BombBullet)game.get(BombBullet.class);
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
        BaseGame game = BaseGame.get();
        x += speed * dx * game.frameTime;
        y += speed * dy * game.frameTime;

        dy += GRAVITY * game.frameTime;

        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();
        boolean toBeDeleted = false;

        if (x < -9999 || x > w + 9999) {
            toBeDeleted = true;
        }
        if (y < -9999 || y > h + 9999) {
            toBeDeleted = true;
        }

        if (toBeDeleted) {
            game.remove(this, true);
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

    public void adjustLocationWithBackground(float x, float y) {
        this.x -= x;
        this.y -= y;
    }
}
