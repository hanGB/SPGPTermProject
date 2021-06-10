package kr.ac.kpu.s2018182039.kingdomrush.game.control;

import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.enemy.EnemyObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainScene;

public class EnemyGenerator implements GameObject {
    private static final float INITIAL_SPAWN_INTERVAL = 2.0f;
    private float time;
    private float spawnInterval;
    private int wave;
    private int stageId;

    public EnemyGenerator(int stageId) {
        time = INITIAL_SPAWN_INTERVAL;
        spawnInterval = INITIAL_SPAWN_INTERVAL;
        wave = 0;
        this.stageId = stageId;
    }

    @Override
    public void update() {
        BaseGame game = BaseGame.get();
        time += game.frameTime;
        if (time >= spawnInterval) {
            generate();
            time -= spawnInterval;
        }
    }

    private void generate() {
        wave++;
        BaseGame game = BaseGame.get();
        EnemyObject enemy = new EnemyObject(300, 300);
        game.add(MainScene.Layer.enemy.ordinal(), enemy);
    }

    @Override
    public void draw(Canvas canvas) {
        // does nothing
    }
}
