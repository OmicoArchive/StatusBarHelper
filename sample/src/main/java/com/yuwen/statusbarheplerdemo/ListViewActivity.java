
package com.yuwen.statusbarheplerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.yuwen.support.util.statusbar.StatusBarHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends Activity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.statusbar_immer_layout);
        mListView = (ListView) findViewById(R.id.sb_show_list);
        List<Map<String, Object>> contents = new ArrayList<>();
        for (int i = 0; i < 100; ) {
            Map<String, Object> map = new HashMap<>();
            if (i % 2 == 0) {
                map.put("ICON", R.mipmap.ic_launcher);
                map.put("TITLE", ++i + "  Test Title one");
                map.put("CONTENT", "Test Content one");
            } else {
                map.put("ICON", R.mipmap.ic_launcher);
                map.put("TITLE", ++i + "  Test Title two Title two");
                map.put("CONTENT", "Test Content two Test Content two");
            }
            contents.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, contents, R.layout.sb_list_item_layout,
                new String[]{
                        "ICON", "TITLE", "CONTENT"
                },
                new int[]{
                        android.R.id.icon, android.R.id.text1, android.R.id.text2
                });
        mListView.setAdapter(adapter);

        //设置状态栏透明
        StatusBarHelper.setImmersiveWindow(getWindow(), true);
        //设置状态栏图标文字为深色

        StatusBarHelper.setStatusBarDarkMode(getWindow(),true);

        StatusBarHelper.setStatusBarHeight(ListViewActivity.this, mListView);
    }
}
