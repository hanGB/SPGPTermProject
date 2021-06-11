package kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.StaticGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainGame;

public class BattleHUD implements GameObject {
    private StaticGameBitmap hudBitmap;
    private Score hpNum;
    private Score goldNum;
    private Score waveNum;

    private final float x;
    private final float y;

    private boolean gameEnd;

    public BattleHUD(float x, float y) {
        hudBitmap = new StaticGameBitmap(R.mipmap.battle_ui, 0, 0, 226, 78, 2);
        this.x = x;
        this.y = y;

        hpNum = new Score((int)x - 60, (int)y - 65, 1.5f);
        hpNum.setScore(20);

        goldNum = new Score((int)x + 200, (int)y - 65, 1.5f);
        goldNum.setScore(300);

        waveNum = new Score((int)x + 50, (int)y + 20, 1.5f);
        waveNum.setScore(1);

        gameEnd = false;
    }

    @Override
    public void update() {
        if (!gameEnd) {
            hpNum.update();
            goldNum.update();
            waveNum.update();

            if (hpNum.getScore() <= 0) {
                gameEnd = true;
                showEndLog();
            }
        }
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
    public boolean isNotExpensive(int gold){
        return goldNum.isNotExpensive(gold);
    }

    public void showEndLog() {
        MainGame game = (MainGame)BaseGame.get();

        AlertDialog.Builder builder = new AlertDialog.Builder(game.context);
        builder.setTitle("사망");
        builder.setMessage(waveNum.getScore() + " 웨이브까지 생존하였습니다");
        builder.setPositiveButton("스테이지 선택으로", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                game.popScene();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
