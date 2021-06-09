package kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class StaticGameBitmap extends GameBitmap {
    private final int pixel_size;
    private final int left;
    private final int top;
    private final int right;
    private final int bottom;

    private Bitmap bitmap;
    private final int imageWidth;
    private final int imageHeight;

    public StaticGameBitmap(int resId, int left, int top, int right, int bottom, int pixel_size) {
        bitmap = GameBitmap.load(resId);
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.pixel_size = pixel_size;
    }

    public StaticGameBitmap(int resId, int pixel_size) {
        bitmap = GameBitmap.load(resId);
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
        this.left = 0;
        this.top = 0;
        this.right = imageWidth;
        this.bottom = imageHeight;
        this.pixel_size = pixel_size;
    }

    public void changeImage(int resId) {
        bitmap = GameBitmap.load(resId);
    }

    public void draw(Canvas canvas, float x, float y) {
        int w = right - left;
        int h = bottom - top;
        int hw = w / 2 * pixel_size;
        int hh = h / 2 * pixel_size;

        Rect src = new Rect(left, top, right, bottom);
        RectF dst = new RectF(x - hw, y - hh, x + hw, y + hh);

        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public int getWidth() {
        return (right - left) * pixel_size;
    }
    public int getHeight() {
        return (bottom - top) * pixel_size;
    }

    public void getBoundingRect(float x, float y, RectF rect) {
        int hw = getWidth() / 2;
        int hh = getHeight() / 2;

        float dl = x - hw * pixel_size;
        float dt = y - hh * pixel_size;
        float dr = x + hw * pixel_size;
        float db = y + hh * pixel_size;

        rect.set(dl, dt, dr, db);
    }
}
