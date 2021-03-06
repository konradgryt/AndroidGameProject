package com.example.konrad.androidgameproject.View;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Vibrator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.konrad.androidgameproject.R;
import com.example.konrad.androidgameproject.Controller.SoundService;

public class Options extends AppCompatActivity {

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
            btnOptionsAnimations.setBackground(disabled);
        }
    }

    public void onClickMusic(View view) {
        playAnimationOnView(view,Techniques.Pulse);
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
        playAnimationOnView(view,Techniques.Pulse);
        if (!areVibrationsOn) {
            Drawable active = getResources().getDrawable( R.drawable.active );
            btnOptionsVibrations.setBackground(active);
            areVibrationsOn = true;
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(500);
            }
        } else if (areVibrationsOn) {
            Drawable disabled = getResources().getDrawable( R.drawable.disabled );
            btnOptionsVibrations.setBackground(disabled);
            areVibrationsOn = false;
        }
    }

    public void onClickAnimations(View view) {
        playAnimationOnView(view,Techniques.Pulse);
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
//        AlertDialog alertDialog = new AlertDialog.Builder(Options.this).create();
//        alertDialog.setTitle("Deleting all data");
//        alertDialog.setMessage("It will delete your profile and highscore");
//        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences shared = getSharedPreferences("App_settings", MODE_PRIVATE);
                        Editor editor = shared.edit();
                        editor.clear();
                        editor.apply();
                        Toast.makeText(Options.this,"DATA DELETED", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }
//                });
//        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//        alertDialog.show();
    }

    private void playAnimationOnView(View view, Techniques technique) {
        if (Options.areAnimationsOn) {
            YoYo.with(technique).duration(500).repeat(0).playOn(view);
        }
    }
}
