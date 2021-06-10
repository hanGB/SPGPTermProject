package kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui;

import android.graphics.Rect;

import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.menu.StageMenuScene;

public class YesNoButton extends ButtonObject {
    boolean result;

    public YesNoButton(int resId, int resIdPressed, float x, float y, Rect rect, Rect rectPressed, int pixel_size, boolean result) {
        super(resId, resIdPressed, x, y, rect, rectPressed, pixel_size);
        this.result = result;
    }

    @Override
    public void processButton() {
        StageMenuScene scene = (StageMenuScene)BaseGame.get().getTopScene();
        scene.processButtonResult(result);
    }

}
