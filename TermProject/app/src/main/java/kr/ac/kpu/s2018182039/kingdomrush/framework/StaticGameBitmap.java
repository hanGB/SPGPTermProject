package kr.ac.kpu.s2018182039.kingdomrush.framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.s2018182039.kingdomrush.ui.view.GameView;

public class StaticGameBitmap extends kr.ac.kpu.s2018182039.kingdomrush.framework.GameBitmap {
    private static final int PIXEL_SIZE = 2;
    private final int left;
    private final int top;
    private final int right;
    private final int bottom;

    private Bitmap bitmap;
    private final int imageWidth;
    private final int imageHeight;

    public StaticGameBitmap(int resId, int left, int top, int right, int bottom) {
        bitmap = kr.ac.kpu.s2018182039.kingdomrush.framework.GameBitmap.load(resId);
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void draw(Canvas canvas, float x, float y) {
        int w = right - left;
        int h = bottom - top;
        int hw = w / 2 * PIXEL_SIZE;
        int hh = h / 2 * PIXEL_SIZE;

        Rect src = new Rect(left, top, right, bottom);
        RectF dst = new RectF(x - hw, y - hh, x + hw, y + hh);

        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public int getWidth() {
        return this.imageWidth * PIXEL_SIZE;
    }
    public int getHeight() {
        return this.imageHeight * PIXEL_SIZE;
    }
}
