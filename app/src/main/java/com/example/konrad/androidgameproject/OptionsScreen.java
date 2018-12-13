package com.example.konrad.androidgameproject;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Vibrator;

public class OptionsScreen extends Activity {

    static boolean isMusicOn = true;
    static boolean areVibrationsOn = false;
    static boolean areAnimationsOn = true;
    ImageView btnOptionsMusic;
    ImageView btnOptionsVibrations;
    ImageView btnOptionsAnimations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_options);

        Drawable active = getResources().getDrawable( R.drawable.active );
        Drawable disabled = getResources().getDrawable( R.drawable.disabled);
        //Music
        btnOptionsMusic = findViewById(R.id.btnOptionsMusic);
        if (isMusicOn) {
            btnOptionsMusic.setBackground(active);
        } else if (!isMusicOn) {
            btnOptionsMusic.setBackground(disabled);
        }

        //Vibrations
        btnOptionsVibrations = findViewById(R.id.btnOptionsVibrations);
        if (areVibrationsOn) {
            btnOptionsVibrations.setBackground(active);
        } else if (!areVibrationsOn) {
            btnOptionsVibrations.setBackground(disabled);
        }

        //Animations
        btnOptionsAnimations = findViewById(R.id.btnOptionsAnimations);
        if (areAnimationsOn) {
            btnOptionsAnimations.setBackground(active);
        } else if (!areAnimationsOn) {
            btnOptionsAnimations.setBackground(active);
        }
    }

    public void onClickMusic(View view) {
        Intent sound = new Intent(this, SoundService.class);
        if (!isMusicOn) {
            Drawable active = getResources().getDrawable( R.drawable.active );
            btnOptionsMusic.setBackground(active);
            try {
                startService(sound);
                isMusicOn = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (isMusicOn) {
            Drawable disabled = getResources().getDrawable( R.drawable.disabled );
            btnOptionsMusic.setBackground(disabled);
            stopService(sound);
            isMusicOn = false;
        }
    }
    public void onClickVibrations(View view) {
        if (!areVibrationsOn) {
            Drawable active = getResources().getDrawable( R.drawable.active );
            btnOptionsVibrations.setBackground(active);
            areVibrationsOn = true;
        } else if (areVibrationsOn) {
            Drawable disabled = getResources().getDrawable( R.drawable.disabled );
            btnOptionsVibrations.setBackground(disabled);
            areVibrationsOn = false;
        }
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }

    public void onClickAnimations(View view) {
        if (!areAnimationsOn) {
            Drawable active = getResources().getDrawable( R.drawable.active );
            btnOptionsAnimations.setBackground(active);
            areAnimationsOn = true;
        } else if (areAnimationsOn) {
            Drawable disabled = getResources().getDrawable( R.drawable.disabled );
            btnOptionsAnimations.setBackground(disabled);
            areAnimationsOn = false;
        }
    }


    public void resetData(View view) {
        SharedPreferences shared = getSharedPreferences("App_settings", MODE_PRIVATE);
        Editor editor = shared.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(this,"DATA RESETED", Toast.LENGTH_SHORT).show();
    }
}
