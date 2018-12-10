package com.example.konrad.androidgameproject;

import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainMenu extends AppCompatActivity {
    final int OPEN_DIFFICULTY_CODE = 1000;
    final int OPEN_OPTIONS_CODE = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        try {
          Intent sound = new Intent(this, SoundService.class);
          startService(sound);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView txtMenuPlayerName = findViewById(R.id.txtMenuPlayerName);
        if (data != null) {
            String playerName = data.getStringExtra("PlayerName");
            txtMenuPlayerName.setText(playerName);
        }
    }

    public void startGame(View vew) {
//        Intent getGameIntent = new Intent(this, Game.class);
//        final int result = 1;
//        startActivityForResult(getGameIntent,result);
        Intent getGameIntent = new Intent(this, Difficulty.class);
        startActivityForResult(getGameIntent,OPEN_DIFFICULTY_CODE);
    }

    public void openOptions(View view) {
        Intent getOptionsIntent = new Intent(this, OptionsScreen.class);
        //startActivity(getOptionsIntent);
       startActivityForResult(getOptionsIntent,OPEN_OPTIONS_CODE);
    }
}
