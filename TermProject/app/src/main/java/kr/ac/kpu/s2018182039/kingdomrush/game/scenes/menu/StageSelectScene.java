package kr.ac.kpu.s2018182039.kingdomrush.game.scenes.menu;

import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.Scene;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.MovingBackgroundObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.StageFlagObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainGame;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainScene;

public class StageSelectScene extends Scene {
    private MovingBackgroundObject backgroundMap;

    public enum Layer {
        bg, flag, LAYER_COUNT
    }
    public static StageSelectScene scene;
    public void add(StageSelectScene.Layer layer, GameObject obj) {
        add(layer.ordinal(), obj);
    }
    public ArrayList<GameObject> objectsAt(StageSelectScene.Layer layer) {
        return objectsAt(layer.ordinal());
    }

    private MediaPlayer mediaPlayer;

    @Override
    public void start() {
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        initLayers(Layer.LAYER_COUNT.ordinal());

        backgroundMap = new MovingBackgroundObject(R.mipmap.screen_map_background,
                w / 2, h / 2, 30, 80, 940, 740, 3);
        add(Layer.bg, backgroundMap);

        Rect rect = new Rect(0, 0, 100,120);
        Rect rectPressed = new Rect(0, 120, 100,240);
        StageFlagObject stage1Flag = new StageFlagObject(R.mipmap.flag, R.mipmap.flag,
                w * 1 / 9, h, rect, rectPressed, 2, 1);
        add(Layer.flag, stage1Flag);
        StageFlagObject stage2Flag = new StageFlagObject(R.mipmap.flag, R.mipmap.flag,
                w* 2/5, h * 2 / 5, rect, rectPressed, 2, 2);
        add(Layer.flag, stage2Flag);
        StageFlagObject stage3Flag = new StageFlagObject(R.mipmap.flag, R.mipmap.flag,
                w* 5/6, h / 5, rect, rectPressed, 2, 3);
        add(Layer.flag, stage3Flag);

        MainGame game = (MainGame) BaseGame.get();
        mediaPlayer = MediaPlayer.create(game.context, R.raw.music_map);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.3f,0.3f);
        mediaPlayer.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

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
        if ( backgroundMap.moveX == 0 && backgroundMap.moveY == 0) {
            return;
        }

        for (GameObject o : objectsAt(Layer.flag)) {
            StageFlagObject flag = (StageFlagObject)o;
            flag.adjustLocationWithBackground(backgroundMap.moveX, backgroundMap.moveY);
        }
        backgroundMap.moveX = 0;
        backgroundMap.moveY = 0;
    }

    @Override
    public void end() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void pause() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void resume() {
        MainGame game = (MainGame)BaseGame.get();
        mediaPlayer = MediaPlayer.create(game.context, R.raw.music_map);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.3f,0.3f);
        mediaPlayer.start();
    }

}
