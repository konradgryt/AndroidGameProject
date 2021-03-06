package com.example.konrad.androidgameproject.View;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.konrad.androidgameproject.R;
import com.example.konrad.androidgameproject.Controller.SoundService;
import com.example.konrad.androidgameproject.Controller.Utility;

public class MainMenu extends AppCompatActivity {
    final int OPEN_DIFFICULTY_CODE = 1000;
    final int OPEN_OPTIONS_CODE = 2000;
    final int OPEN_PROFILE_CODE = 3000;
    final int OPEN_CATEGORIES_CODE = 7000;
    ImageView imgViewMenuAvatar;
    TextView txtViewMenuAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        SharedPreferences shared = getSharedPreferences("App_settings", MODE_PRIVATE);
        String currentAvatar = shared.getString("currentAvatar", "empty");
        String currentNickname = shared.getString("currentNickname", "Your nickname");

        imgViewMenuAvatar = findViewById(R.id.imgViewMenuAvatar);
        if (currentAvatar != "empty") {
            imgViewMenuAvatar.setImageBitmap(Utility.decodeBase64(currentAvatar));
        } else {
            Bitmap placeholder = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            imgViewMenuAvatar.setImageBitmap(Bitmap.createScaledBitmap(placeholder,150,150,false));
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
        String currentNickname = shared.getString("currentNickname", "Your nickname");

        imgViewMenuAvatar = findViewById(R.id.imgViewMenuAvatar);
        if (currentAvatar != "empty") {
            imgViewMenuAvatar.setImageBitmap(Bitmap.createScaledBitmap(Utility.decodeBase64(currentAvatar),150,150,false));
        } else {
            Bitmap placeholder = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            imgViewMenuAvatar.setImageBitmap(Bitmap.createScaledBitmap(placeholder,150,150,false));
        }

        txtViewMenuAvatar = findViewById(R.id.txtViewMenuAvatar);
        txtViewMenuAvatar.setText(currentNickname);
        Log.i("Well", currentNickname);
        Log.i("Well", txtViewMenuAvatar.getText().toString());
    }

    public void startGame(View view) {
        playAnimationOnView(view,Techniques.Pulse);
        Intent getGameIntent = new Intent(this, Difficulty.class);
        startActivityForResult(getGameIntent,OPEN_DIFFICULTY_CODE);
    }

    public void openCategories(View view) {
        playAnimationOnView(view,Techniques.Pulse);
        Intent getOptionsIntent = new Intent(this, Category.class);
        //startActivity(getOptionsIntent);
        startActivityForResult(getOptionsIntent,OPEN_CATEGORIES_CODE);
    }

    public void openOptions(View view) {
        playAnimationOnView(view,Techniques.Pulse);
        Intent getOptionsIntent = new Intent(this, Options.class);
        //startActivity(getOptionsIntent);
       startActivityForResult(getOptionsIntent,OPEN_OPTIONS_CODE);
    }

    public void openHelp(View view) {
        playAnimationOnView(view,Techniques.Pulse);
        Toast.makeText(this, "May the force be with you", Toast.LENGTH_SHORT).show();
    }

    public void openProfile(View view) {
        playAnimationOnView(view,Techniques.Pulse);
        Intent getProfileIntent = new Intent(this, Profile.class);
        //startActivity(getOptionsIntent);
        startActivityForResult(getProfileIntent,OPEN_PROFILE_CODE);
    }

    public void quitGame(View view) {
        System.exit(1);
    }

    private void playAnimationOnView(View view, Techniques technique) {
        if (Options.areAnimationsOn) {
            YoYo.with(technique).duration(500).repeat(0).playOn(view);
        }
    }
}
