package com.example.ips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    Animation anim_FadeIn;

    ImageView logo;
    ImageButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.logo);
        loginButton = findViewById(R.id.startbutton);
        anim_FadeIn= AnimationUtils.loadAnimation(this,R.anim.anim_splash_fadein);
        logo.startAnimation(anim_FadeIn);
        loginButton.startAnimation(anim_FadeIn);

        loginButton = findViewById(R.id.startbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}