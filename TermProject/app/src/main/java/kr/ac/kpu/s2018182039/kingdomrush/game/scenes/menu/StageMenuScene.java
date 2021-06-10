package kr.ac.kpu.s2018182039.kingdomrush.game.scenes.menu;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.Scene;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.StageFlagObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.StaticDrawObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.YesNoButton;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainGame;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainScene;

import static kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView.*;

public class StageMenuScene extends Scene {
    public enum Layer {
        bg, button, fg, LAYER_COUNT
    }
    public static StageMenuScene scene;
    public void add(StageMenuScene.Layer layer, GameObject obj) {
        add(layer.ordinal(), obj);
    }
    public ArrayList<GameObject> objectsAt(StageMenuScene.Layer layer) {
        return objectsAt(layer.ordinal());
    }

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
                w * 8 / 9, h * 1 / 6, rect, rectPressed, 2, false);
        add(Layer.button, cancelButton);
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
            game.push(new MainScene());
        }
        else {
            game.popScene();
        }
    }

}
