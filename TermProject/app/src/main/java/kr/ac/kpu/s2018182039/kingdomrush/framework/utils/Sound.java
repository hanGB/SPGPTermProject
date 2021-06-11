package kr.ac.kpu.s2018182039.kingdomrush.framework.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.util.HashMap;

import kr.ac.kpu.s2018182039.kingdomrush.R;

public class Sound {
    private static final String TAG = Sound.class.getSimpleName();
    private static SoundPool soundPool;
    private static final int[] SOUND_IDS = {
            R.raw.archer_ready,
            R.raw.archer_taunt1,
            R.raw.barrack_ready,
            R.raw.barrack_taunt1,
            R.raw.artillery_ready,
            R.raw.artillery_taunt1,
            R.raw.mage_ready,
            R.raw.mage_taunt1,
            R.raw.music_main_menu,
            R.raw.music_map,
            R.raw.sound_arrow_hit3,
            R.raw.sound_arrow_release3,
            R.raw.sound_human_dead1,
            R.raw.sound_bomb1,
            R.raw.sound_enemy_orc_dead,
            R.raw.sound_buy_upgrade,
            R.raw.sound_mage_shot,
            R.raw.sound_next_wave_ready,
            R.raw.sound_open_tower_menu,
            R.raw.sound_tower_open_door,
            R.raw.sound_loose_life,
            R.raw.sound_soldiers_fighting,
            R.raw.sound_battle_axe,
    };
    private static HashMap<Integer, Integer> soundIdMap = new HashMap<>();

    public static void init(Context context) {
        AudioAttributes audioAttributes;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            Sound.soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(3)
                    .build();
        }
        else {
            Sound.soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        }

        for (int resId: SOUND_IDS) {
            int soundId = soundPool.load(context, resId, 1);
            soundIdMap.put(resId, soundId);
        }
    }
    public static int play(int resId) {
        Log.d(TAG, "play: " + resId);
        int soundId = soundIdMap.get(resId);
        int streamId = soundPool.play(soundId, 1f, 1f, 1, 0, 1f);

        return streamId;
    }

    public static int playBG(int resId) {
        Log.d(TAG, "playBG: " + resId);
        int soundId = soundIdMap.get(resId);
        int streamId = soundPool.play(soundId, 1f, 1f, 1, 1, 1.f);
        return streamId;
    }

    public static void stopBG(int streamId) {
        soundPool.stop(streamId);
    }
}
