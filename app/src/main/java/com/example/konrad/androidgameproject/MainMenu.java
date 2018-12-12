package com.example.konrad.androidgameproject;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {
    final int OPEN_DIFFICULTY_CODE = 1000;
    final int OPEN_OPTIONS_CODE = 2000;
    final int OPEN_PROFILE_CODE = 3000;
    ImageView imgViewMenuAvatar;
    TextView txtViewMenuAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        SharedPreferences shared = getSharedPreferences("App_settings", MODE_PRIVATE);
        String currentAvatar = shared.getString("currentAvatar", "empty");
        String currentNickname = shared.getString("currentNickname", "player");

        imgViewMenuAvatar = findViewById(R.id.imgViewMenuAvatar);
        if (currentAvatar != "empty") {
            imgViewMenuAvatar.setImageBitmap(Utility.decodeBase64(currentAvatar));
        } else {
            imgViewMenuAvatar.setImageResource(R.drawable.placeholder);
        }

        txtViewMenuAvatar = findViewById(R.id.txtViewMenuAvatar);
        txtViewMenuAvatar.setText(currentNickname);
        Log.i("Well", currentNickname);
        Log.i("Well", txtViewMenuAvatar.getText().toString());

        try {
          Intent sound = new Intent(this, SoundService.class);
          startService(sound);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences shared = getSharedPreferences("App_settings", MODE_PRIVATE);
        String currentAvatar = shared.getString("currentAvatar", "empty");
        String currentNickname = shared.getString("currentNickname", "player");

        imgViewMenuAvatar = findViewById(R.id.imgViewMenuAvatar);
        if (currentAvatar != "empty") {
            imgViewMenuAvatar.setImageBitmap(Bitmap.createScaledBitmap(Utility.decodeBase64(currentAvatar),100,100,false));
        } else {
            imgViewMenuAvatar.setImageResource(R.drawable.placeholder);
        }

        txtViewMenuAvatar = findViewById(R.id.txtViewMenuAvatar);
        txtViewMenuAvatar.setText(currentNickname);
        Log.i("Well", currentNickname);
        Log.i("Well", txtViewMenuAvatar.getText().toString());
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
        Intent getGameIntent = new Intent(this, Difficulty.class);
        startActivityForResult(getGameIntent,OPEN_DIFFICULTY_CODE);
    }

    public void openOptions(View view) {
        Intent getOptionsIntent = new Intent(this, OptionsScreen.class);
        //startActivity(getOptionsIntent);
       startActivityForResult(getOptionsIntent,OPEN_OPTIONS_CODE);
    }

    public void openProfile(View view) {
        Intent getProfileIntent = new Intent(this, ProfileScreen.class);
        //startActivity(getOptionsIntent);
        startActivityForResult(getProfileIntent,OPEN_PROFILE_CODE);
    }

    public void quitGame(View view) {
        System.exit(1);
    }
}
