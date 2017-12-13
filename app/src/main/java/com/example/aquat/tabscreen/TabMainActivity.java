package com.example.aquat.tabscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class TabMainActivity extends FragmentActivity {

    //region 定義
    public enum Page {
        Comment(0),
        Ditail(1),;

        private final int id;

        private Page(final int id) {
            this.id = id;
        }

        public int getInt() {
            return  this.id;
        }


    }
    //endregion

    ViewPager viewPager;
    // キーボード表示を制御するためのオブジェクト
    InputMethodManager inputMethodManager;
    private LinearLayout mainlayout;

    private UserInfo userInfo;

    private List<ProductInfo> sampleDataList;
    private CommentJson commentData;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);

        //ユーザー情報を受け取る
        userInfo = (UserInfo)getIntent().getSerializableExtra("userInfo");

       //アダプター作成
        TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager(),userInfo);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mainlayout = (LinearLayout)findViewById(R.id.main_layout);

        //タブボタン設定
        ViewGroup vg = (ViewGroup)findViewById(R.id.tab);
        //Layout取得
        getLayoutInflater().inflate(R.layout.tab_layout,vg);
        Button commentBtn = (Button)(vg.findViewById(R.id.tab_comment));
        Button ditailBtn = (Button)(vg.findViewById(R.id.tab_ditail));
       /* Button commentBtn = (Button)(findViewById(R.id.tab_comment));
        Button ditailBtn = (Button)(findViewById(R.id.tab_ditail));*/

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(Page.Comment.getInt());
                inputMethodManager.hideSoftInputFromWindow(mainlayout.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                mainlayout.requestFocus();
            }
        });

        ditailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(Page.Ditail.getInt());
                //キーボードを隠す
                inputMethodManager.hideSoftInputFromWindow(mainlayout.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                mainlayout.requestFocus();
            }
        });


        viewPager.setOnTouchListener(new ViewPager.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //キーボードを隠す
                inputMethodManager.hideSoftInputFromWindow(mainlayout.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                mainlayout.requestFocus();
                return false;
            }
        });
    }
}
