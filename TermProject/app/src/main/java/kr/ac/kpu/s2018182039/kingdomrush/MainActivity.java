package kr.ac.kpu.s2018182039.kingdomrush;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainGame;

public class MainActivity extends AppCompatActivity {

    private MainGame mainGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainGame = new MainGame();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GameView.view.pauseGame();
    }

    @Override
    protected void onResume() {
        GameView.view.resumeGame();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (GameView.view.handleBackKey()) {
            return;
        }
        super.onBackPressed();
    }
}