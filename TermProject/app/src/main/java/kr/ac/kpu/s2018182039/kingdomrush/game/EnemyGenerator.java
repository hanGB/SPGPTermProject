package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.ui.view.GameView;

public class EnemyGenerator implements GameObject {
    private static final float INITIAL_SPAWN_INTERVAL = 2.0f;
    private float time;
    private float spawnInterval;
    private int wave;

    public EnemyGenerator() {
        time = INITIAL_SPAWN_INTERVAL;
        spawnInterval = INITIAL_SPAWN_INTERVAL;
        wave = 0;
    }

    @Override
    public void update() {
        MainGameState state = MainGameState.get();
        time += state.frameTime;
        if (time >= spawnInterval) {
            generate();
            time -= spawnInterval;
        }
    }

    private void generate() {
        wave++;
        MainGameState state = MainGameState.get();
        EnemyObject enemy = new EnemyObject(300, 300);
        state.add(MainGameState.Layer.enemy, enemy);
    }

    @Override
    public void draw(Canvas canvas) {
        // does nothing
    }
}
