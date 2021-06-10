package kr.ac.kpu.s2018182039.kingdomrush.game.scenes.menu;

import android.graphics.Rect;
import android.media.audiofx.DynamicsProcessing;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.Scene;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.MovingBackgroundObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.MovingButtonObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.StageFlagObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainScene;

public class StageMenuScene extends Scene {
    private MovingBackgroundObject backgroundMap;

    public enum Layer {
        bg, flag, LAYER_COUNT
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

        Rect rect = new Rect(0, 0, 100,120);
        Rect rectPressed = new Rect(0, 120, 100,240);
        StageFlagObject stage1Flag = new StageFlagObject(R.mipmap.flag, R.mipmap.flag,
                w/2, h / 2, rect, rectPressed, 2, 1);
        add(Layer.flag, stage1Flag);
        StageFlagObject stage2Flag = new StageFlagObject(R.mipmap.flag, R.mipmap.flag,
                w/3, h / 3, rect, rectPressed, 2, 1);
        add(Layer.flag, stage2Flag);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        Log.d("test", "" + action + "");
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            backgroundMap.pressMove(event.getX(), event.getY());

            for (GameObject o : objectsAt(Layer.flag)) {
                StageFlagObject flag = (StageFlagObject)o;
                flag.pressOn(event.getX(), event.getY(), true);
            }
            return true;
        }
        else if (action == MotionEvent.ACTION_UP) {
            backgroundMap.pressUp();

            for (GameObject o : objectsAt(Layer.flag)) {
                StageFlagObject flag = (StageFlagObject)o;
                flag.pressOn(event.getX(), event.getY(), false);
            }
            return true;
        }

        return false;
    }

    @Override
    public void adjustLocation() {
        for (GameObject o : objectsAt(Layer.flag)) {
            StageFlagObject flag = (StageFlagObject)o;
            flag.adjustLocationWithBackground(backgroundMap.moveX, backgroundMap.moveY);
        }
        backgroundMap.moveX = 0;
        backgroundMap.moveY = 0;
    }

}
