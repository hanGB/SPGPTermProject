package kr.ac.kpu.s2018182039.kingdomrush.game.control;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.utils.Sound;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.enemy.EnemyObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainScene;

public class EnemyGenerator implements GameObject {
    private static float INITIAL_SPAWN_INTERVAL = 2.0f;
    private static final int WAVE_ENEMY_COUNT = 10;
    private static final int INCREASE_HP_PER_WAVE = 2;

    private float time;
    private float spawnInterval;
    private int wave;
    private int stageId;
    private int hp;
    private int damage;
    private int generateCount;
    private Random random = new Random();

    public EnemyGenerator(int stageId) {
        time = INITIAL_SPAWN_INTERVAL;
        spawnInterval = INITIAL_SPAWN_INTERVAL;
        wave = 0;
        this.stageId = stageId;
        hp = 20;
        damage = 5;
        generateCount = 0;

        INITIAL_SPAWN_INTERVAL = 2.5f - stageId * 0.5f;
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
        if (generateCount > WAVE_ENEMY_COUNT) {
            generateCount = 0;
            wave++;
            Sound.play(R.raw.sound_next_wave_ready);

            BaseGame game = BaseGame.get();
            MainScene scene = (MainScene)game.getTopScene();
            scene.increaseWave(1);
        }

        BaseGame game = BaseGame.get();
        EnemyObject enemy;
        if (stageId == 1) {
            enemy = new EnemyObject(300, 300, hp + wave * INCREASE_HP_PER_WAVE * stageId, damage + wave * stageId, false);
        }
        else {
            if (random.nextInt(2) == 1) {
                enemy = new EnemyObject(300, 300, hp + wave * INCREASE_HP_PER_WAVE * stageId, damage + wave * stageId, false);
            }
            else {
               enemy = new EnemyObject(300, 300, hp + wave * INCREASE_HP_PER_WAVE * stageId, damage + wave * stageId, true);
            }
        }
        game.add(MainScene.Layer.enemy.ordinal(), enemy);

        generateCount++;
    }

    @Override
    public void draw(Canvas canvas) {
        // does nothing
    }
}
