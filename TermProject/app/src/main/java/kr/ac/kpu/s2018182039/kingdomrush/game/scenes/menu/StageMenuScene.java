package kr.ac.kpu.s2018182039.kingdomrush.game.scenes.menu;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.Scene;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.StaticDrawObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.YesNoButton;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainScene;

import static kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView.*;

public class StageMenuScene extends Scene {
    public enum Layer {
        bg, thumbs, button, text, fg, LAYER_COUNT
    }
    public static StageMenuScene scene;
    public void add(StageMenuScene.Layer layer, GameObject obj) {
        add(layer.ordinal(), obj);
    }
    public ArrayList<GameObject> objectsAt(StageMenuScene.Layer layer) {
        return objectsAt(layer.ordinal());
    }

    private int[] stageNameBitmaps = {
            R.mipmap.stage_1_name,
            R.mipmap.stage_2_name,
            R.mipmap.stage_3_name,
    };
    private int[] stageInformationBitmaps = {
            R.mipmap.stage_1_information,
            R.mipmap.stage_2_information,
            R.mipmap.stage_3_information,
    };
    private int[] stageThumbsBitmaps = {
            R.mipmap.screen_map_stage_thumbs_1,
            R.mipmap.screen_map_stage_thumbs_2,
            R.mipmap.screen_map_stage_thumbs_3,
    };
    public int stageNumber = 3;

    @Override
    public void start() {
        int w = view.getWidth();
        int h = view.getHeight();

        initLayers(Layer.LAYER_COUNT.ordinal());

        StaticDrawObject background
                = new StaticDrawObject(R.mipmap.screen_map_1, w/2, h/2, 0, 0, 1180, 646, 2);

        add(Layer.bg, background);

        StaticDrawObject foreground
                = new StaticDrawObject(R.mipmap.screen_map_1, w * 1/5, h * 2/5, 6, 652, 561, 1080, 2);

        add(Layer.fg, foreground);

        Rect rect = new Rect(0, 162, 174,324);
        Rect rectPressed = new Rect(0, 0, 174,162);
        YesNoButton playButton = new YesNoButton(R.mipmap.play, R.mipmap.play,
                w * 6 / 7, h * 4 / 5, rect, rectPressed, 2, true);
        add(Layer.button, playButton);

        rect.set(0, 89, 92,178);
        rectPressed.set(0, 0, 92,89);
        YesNoButton cancelButton = new YesNoButton(R.mipmap.cancel, R.mipmap.cancel,
                w * 9 / 10, h * 1 / 7, rect, rectPressed, 2, false);
        add(Layer.button, cancelButton);

        add(Layer.text, new StaticDrawObject(stageNameBitmaps[stageNumber - 1],
                w * 6 / 9, h * 1 / 8, 0, 0, 395, 85, 1));

        add(Layer.text, new StaticDrawObject(stageInformationBitmaps[stageNumber - 1],
                w * 11 / 14, h * 4 / 9, 0, 0, 1000, 500, 1));

        add(Layer.thumbs, new StaticDrawObject(stageThumbsBitmaps[stageNumber - 1],
                w * 1 / 4, h * 1 / 3, 0, 0, 496, 444, 2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            for (GameObject o : objectsAt(Layer.button)) {
                YesNoButton button = (YesNoButton)o;
                button.pressOn(event.getX(), event.getY(), true);
            }
            return true;
        }
        else if (action == MotionEvent.ACTION_UP) {
            for (GameObject o : objectsAt(Layer.button)) {
                YesNoButton button = (YesNoButton)o;
                button.pressOn(event.getX(), event.getY(), false);
            }
            return true;
        }

        return false;
    }

    public void processButtonResult(boolean result) {
        BaseGame game = BaseGame.get();
        if (result) {
            MainScene scene = new MainScene();
            scene.stageId = this.stageNumber;
            game.push(scene);
        }
        else {
            game.popScene();
        }
    }

}
