package com.task.coronavirusupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intro=findViewById(R.id.intro);

        Animation animation=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_anim);
        intro.setAnimation(animation);
        animation.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,CountryDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);

    }
}
