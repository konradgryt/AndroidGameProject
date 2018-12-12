package com.example.konrad.androidgameproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

public class OptionsScreen extends Activity {

    static boolean isMusicOn = true;
    static boolean isDarkModeOn = true;
    Button btnOptionMusic;
    Button btnOptionsDarkMode;
    TableLayout optionsLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_options);

        //Music
        btnOptionMusic = findViewById(R.id.btnOptionsMusic);
        if (isMusicOn) {
            btnOptionMusic.setText(R.string.button_on);
        } else if (!isMusicOn) {
            btnOptionMusic.setText(R.string.button_off);
        }

        //Dark mode
        btnOptionsDarkMode = findViewById(R.id.btnOptionsDarkMode);
        optionsLayout = findViewById(R.id.optionsLayout);
        if (isDarkModeOn) {
            optionsLayout.setBackgroundColor(Color.BLACK);
            btnOptionsDarkMode.setText(R.string.button_on);
        } else if (!isDarkModeOn) {
            optionsLayout.setBackgroundColor(Color.DKGRAY);
            btnOptionsDarkMode.setText(R.string.button_off);
        }
    }

    public void confirmOptions(View view) {
        EditText playerNameEditText = findViewById(R.id.txtPlayerName);
        String playerNameString = String.valueOf(playerNameEditText.getText());
        Intent goBack = new Intent();
        goBack.putExtra("PlayerName", playerNameString);
        setResult(RESULT_OK, goBack);
        finish();
    }

    public void cancelOptions(View view) {
        setResult(RESULT_OK);
        finish();
    }

    public void onClickMusic(View view) {
        Intent sound = new Intent(this, SoundService.class);
        if (btnOptionMusic.getText().toString().equals("Off")) {
            btnOptionMusic.setText(getString(R.string.button_on));
            try {
                startService(sound);
                isMusicOn = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (btnOptionMusic.getText().toString().equals("On")) {
            btnOptionMusic.setText(getString(R.string.button_off));
            stopService(sound);
            isMusicOn = false;
        }
    }

    public void onClickDarkMode(View view) {
        optionsLayout = findViewById(R.id.optionsLayout);
        if (btnOptionsDarkMode.getText().toString().equals("Off")) {
            btnOptionsDarkMode.setText(getString(R.string.button_on));
            optionsLayout.setBackgroundColor(Color.BLACK);
            isDarkModeOn = true;
        } else if (btnOptionsDarkMode.getText().toString().equals("On")) {
            btnOptionsDarkMode.setText(getString(R.string.button_off));
            optionsLayout.setBackgroundColor(Color.DKGRAY);
            isDarkModeOn = false;
        }
    }


    public void resetData(View view) {
        SharedPreferences shared = getSharedPreferences("App_settings", MODE_PRIVATE);
        Editor editor = shared.edit();
        editor.clear();
        editor.apply();
    }
}
