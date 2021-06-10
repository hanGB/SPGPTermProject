package kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui;

import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.StaticGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;

public class BattleHUD implements GameObject {
    private StaticGameBitmap hudBitmap;
    private Score hpNum;
    private Score goldNum;
    private Score waveNum;

    private final float x;
    private final float y;

    public BattleHUD(float x, float y) {
        hudBitmap = new StaticGameBitmap(R.mipmap.battle_ui, 0, 0, 226, 78, 2);
        this.x = x;
        this.y = y;

        hpNum = new Score((int)x - 60, (int)y - 65, 1.5f);
        hpNum.setScore(20);

        goldNum = new Score((int)x + 200, (int)y - 65, 1.5f);
        goldNum.setScore(500);

        waveNum = new Score((int)x + 50, (int)y + 20, 1.5f);
        waveNum.setScore(1);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        hudBitmap.draw(canvas, x, y);
        hpNum.draw(canvas);
        goldNum.draw(canvas);
        waveNum.draw(canvas);
    }

    public void addHp(int hp) {
        hpNum.addScore(hp);
    }
    public void addGold(int gold) {
        goldNum.addScore(gold);
    }
    public void addWave(int wave) {
        waveNum.addScore(wave);
    }
}
