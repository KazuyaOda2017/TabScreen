package com.example.aquat.tabscreen;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TabMainActivity extends FragmentActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(
                new TabFragmentAdapter(
                        getSupportFragmentManager()));

        //タブボタン設定
       /* ViewGroup vg = (ViewGroup)findViewById(R.id.tab);
        //Layout取得
        getLayoutInflater().inflate(R.layout.tab_layout,vg);
        Button commentBtn = (Button)(vg.findViewById(R.id.tab_comment));
        Button ditailBtn = (Button)(vg.findViewById(R.id.tab_ditail));*/
        Button commentBtn = (Button)(findViewById(R.id.tab_comment));
        Button ditailBtn = (Button)(findViewById(R.id.tab_ditail));

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        ditailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        //Button comentBtn =

        viewPager.setOnTouchListener(new ViewPager.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }
}
