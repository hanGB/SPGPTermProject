package kr.ac.kpu.s2018182039.kingdomrush.game.scenes.menu;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.Scene;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.MovingBackgroundObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainScene;

public class StageMenuScene extends Scene {
    private MovingBackgroundObject backgroundMap;

    public enum Layer {
        bg, LAYER_COUNT
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
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        initLayers(MainScene.Layer.LAYER_COUNT.ordinal());

        backgroundMap = new MovingBackgroundObject(R.mipmap.screen_map_background,
                w / 2, h / 2, 30, 80, 940, 740, 3);

        add(Layer.bg, backgroundMap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            backgroundMap.pressDown(event.getX(), event.getY());
            return true;
        }
        else if (action == MotionEvent.ACTION_MOVE) {
            backgroundMap.pressMove(event.getX(), event.getY());
            return true;
        }
        else if (action == MotionEvent.ACTION_UP) {
            backgroundMap.pressUp();
            return true;
        }
        return false;
    }
}
