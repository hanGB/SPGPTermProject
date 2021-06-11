package kr.ac.kpu.s2018182039.kingdomrush.game.scenes.menu;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.Scene;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.utils.Sound;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.MovingButtonObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.StaticDrawObject;

public class TitleMenuScene extends Scene {
    public enum Layer {
        bg, button, title, LAYER_COUNT
    }
    public static TitleMenuScene scene;

    private MovingButtonObject startButton;

    private int bgmStreamId;

    public void add(TitleMenuScene.Layer layer, GameObject obj) {
        add(layer.ordinal(), obj);
    }
    public ArrayList<GameObject> objectsAt(TitleMenuScene.Layer layer) {
        return objectsAt(layer.ordinal());
    }

    @Override
    public void start() {
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        initLayers(Layer.LAYER_COUNT.ordinal());

        StaticDrawObject background
                = new StaticDrawObject(R.mipmap.screen_slots, w/2, h/2, 6, 6, 1392, 646, 2);

        StaticDrawObject title
                = new StaticDrawObject(R.mipmap.screen_slots, w/2, h/3, 920, 650, 1387, 977, 2);

        Rect rect = new Rect(6, 6, 334,290);
        Rect rectPressed = new Rect(340, 6, 668,290);
        startButton = new MovingButtonObject(R.mipmap.screens_common_ko, R.mipmap.screens_common_ko,
                w/2, h * 5 / 10,w/2, h * 7 / 10, rect, rectPressed, 2);

        add(Layer.bg, background);
        add(Layer.button, startButton);
        add(Layer.title, title);

        bgmStreamId = Sound.playBG(R.raw.music_main_menu);
    }

    @Override
    public void end() {
        Sound.stopBG(bgmStreamId);
    }

    @Override
    public void pause() {
        Sound.stopBG(bgmStreamId);
    }

    @Override
    public void resume() {
        bgmStreamId = Sound.playBG(R.raw.music_main_menu);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            startButton.pressOn(event.getX(), event.getY(), true);
            return true;
        }
        else if (action == MotionEvent.ACTION_UP) {
            startButton.pressOn(event.getX(), event.getY(), false);
            return true;
        }

        return false;
    }
}
