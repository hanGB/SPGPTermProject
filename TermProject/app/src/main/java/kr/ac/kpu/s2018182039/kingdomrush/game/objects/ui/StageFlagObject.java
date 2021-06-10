package kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui;

import android.graphics.Rect;
import android.text.style.UpdateAppearance;

import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.StaticGameBitmap;

public class StageFlagObject extends ButtonObject {

    private final int stageId;

    public StageFlagObject(int resId, int resIdPressed, float x, float y, Rect rect, Rect rectPressed, int pixel_size, int stageId) {
        super(resId, resIdPressed, x, y, rect, rectPressed, pixel_size);
        this.stageId = stageId;
    }

    @Override
    public void processButton() {

    }

    public void adjustLocationWithBackground(float x, float y) {
        this.x -= x;
        this.y -= y;
    }
}
