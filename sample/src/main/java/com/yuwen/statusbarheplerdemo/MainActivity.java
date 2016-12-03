package com.yuwen.statusbarheplerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    RelativeLayout demo1, demo2, demo3,demo4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        demo1 = (RelativeLayout) findViewById(R.id.demo1);
        demo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListViewActivity.class));
            }
        });

        demo2 = (RelativeLayout) findViewById(R.id.demo2);
        demo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FullscreenActivity.class));
            }
        });

        demo3 = (RelativeLayout) findViewById(R.id.demo3);
        demo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ScrollingActivity.class));
            }
        });

        demo4 = (RelativeLayout) findViewById(R.id.demo4);
        demo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NavigationDrawerScrollingActivity.class));
            }
        });
    }
}
