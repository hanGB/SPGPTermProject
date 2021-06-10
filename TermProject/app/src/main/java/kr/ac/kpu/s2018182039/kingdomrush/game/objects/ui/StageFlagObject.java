package kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui;

import android.graphics.Rect;

import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.ButtonObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.menu.StageMenuScene;

public class StageFlagObject extends ButtonObject {

    private final int stageId;

    public StageFlagObject(int resId, int resIdPressed, float x, float y, Rect rect, Rect rectPressed, int pixel_size, int stageId) {
        super(resId, resIdPressed, x, y, rect, rectPressed, pixel_size);
        this.stageId = stageId;
    }

    @Override
    public void processButton() {
        BaseGame game = BaseGame.get();
        game.push(new StageMenuScene());
    }

    public void adjustLocationWithBackground(float x, float y) {
        this.x -= x;
        this.y -= y;
    }
}
