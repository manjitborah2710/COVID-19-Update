package com.task.coronavirusupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class About extends AppCompatActivity {

    TextView githubLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        githubLink=findViewById(R.id.about_tv);
        githubLink.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
