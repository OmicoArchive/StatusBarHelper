package com.yuwen.statusbarheplerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yuwen.support.util.statusbar.StatusBarHelper;

public class FullscreenActivity extends AppCompatActivity {
    boolean mIsDark = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        //设置状态栏透明
        StatusBarHelper.setImmersiveWindow(getWindow(), true);

        Button mStatusConf;
        mStatusConf = (Button) findViewById(R.id.dummy_button);
        mStatusConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mIsDark = !mIsDark;
                //切换状态栏主题颜色风格
                boolean successful = StatusBarHelper.setStatusBarDarkMode(getWindow(), mIsDark);
                String string;
                if (successful) {
                    string = mIsDark ? "已设置状态栏深色主题" : "已设置状态栏浅色主题";
                } else {
                    string = "设置状态栏颜色失败";
                }
                Toast.makeText(FullscreenActivity.this, string, Toast.LENGTH_SHORT).show();
            }
        });
    }
}