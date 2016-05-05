package com.ilmare.swipedeleteitem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.ilmare.swipedeleteitem.Adapter.MySwipeAdapter;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv_list);
        listView.setAdapter(new MySwipeAdapter(this));
    }
}
