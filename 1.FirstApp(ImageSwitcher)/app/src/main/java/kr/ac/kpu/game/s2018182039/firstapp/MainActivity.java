package kr.ac.kpu.game.s2018182039.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private ImageView iv;
    private int pageNumber = 1;
    private static int[] resIds = {
            R.mipmap.cat1,
            R.mipmap.cat2,
            R.mipmap.cat3,
            R.mipmap.cat4,
            R.mipmap.cat5
    };
    private ImageButton prevButton;
    private ImageButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        iv = findViewById(R.id.imageView);
        prevButton = findViewById(R.id.button_prev);
        nextButton = findViewById(R.id.button_next);

        showTextAndImage();
    }

    public void onBtnPrev(View view) {
        if (pageNumber == 1) {
            return;
        }

        pageNumber--;
        showTextAndImage();
    }

    private void showTextAndImage() {
        tv.setText(pageNumber + " / " + resIds.length);
        iv.setImageResource(resIds[pageNumber - 1]);

        prevButton.setEnabled(pageNumber != 1);
        nextButton.setEnabled(pageNumber != resIds.length);
    }

    public void onBtnNext(View view) {
        if (pageNumber == resIds.length) {
            return;
        }

        pageNumber++;
        showTextAndImage();
    }
}
