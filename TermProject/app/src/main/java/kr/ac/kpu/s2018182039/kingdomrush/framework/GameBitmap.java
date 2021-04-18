package kr.ac.kpu.s2018182039.kingdomrush.framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.HashMap;

import kr.ac.kpu.s2018182039.kingdomrush.ui.view.GameView;

public class GameBitmap {
    private static HashMap<Integer, Bitmap> bitmaps = new HashMap<Integer, Bitmap>();

    public static Bitmap load(int resId) {
        Bitmap bitmap = bitmaps.get(resId);
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inScaled = false;
            bitmap = BitmapFactory.decodeResource(res, resId, opts);
            bitmaps.put(resId, bitmap);
        }
        return bitmap;
    }
}
