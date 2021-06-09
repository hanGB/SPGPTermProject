package kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.Scene;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;
import kr.ac.kpu.s2018182039.kingdomrush.game.control.EnemyGenerator;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.tower.TowerBuilder;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.tower.TowerObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.StaticDrawObject;

public class MainScene extends Scene {
    public enum Layer {
        bg, tower, enemy, friendly, towerBuilder, bullet, bomb, controller, LAYER_COUNT
    }
    public static MainScene scene;
    public void add(Layer layer, GameObject obj) {
        add(layer.ordinal(), obj);
    }
    public ArrayList<GameObject> objectsAt(Layer layer) {
        return objectsAt(layer.ordinal());
    }

    @Override
    public void start() {
        scene = this;
        super.start();
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        initLayers(Layer.LAYER_COUNT.ordinal());

        StaticDrawObject background
                = new StaticDrawObject(R.mipmap.test_map_background,
                w / 2, h / 2, 160, 175, 420, 320, 8);

        add(Layer.bg, background);
        add(Layer.controller, new EnemyGenerator());
        add(Layer.towerBuilder, new TowerBuilder(600, 600));
        add(Layer.towerBuilder, new TowerBuilder(1000, 300));
        add(Layer.towerBuilder, new TowerBuilder(1400, 600));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        ArrayList<GameObject> towerBuilders = layers.get(Layer.towerBuilder.ordinal());
        ArrayList<GameObject> towers = layers.get(Layer.tower.ordinal());
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            for (GameObject o  : towerBuilders){
                TowerBuilder builder = (TowerBuilder)o;
                builder.pressOn(event.getX(), event.getY(), true);
            }
            for (GameObject o  : towers){
                TowerObject tower = (TowerObject)o;
                tower.pressOn(event.getX(), event.getY(), true);
            }

            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            for (GameObject o  : towerBuilders){
                TowerBuilder builder = (TowerBuilder)o;
                builder.pressOn(event.getX(), event.getY(), false);
            }
            for (GameObject o  : towers){
                TowerObject tower = (TowerObject)o;
                tower.pressOn(event.getX(), event.getY(), false);
            }
            return true;
        }
        return false;
    }
}
