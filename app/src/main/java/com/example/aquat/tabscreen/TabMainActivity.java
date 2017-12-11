package com.example.aquat.tabscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
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
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(
                new TabFragmentAdapter(
                        getSupportFragmentManager()));

        //ユーザー情報を受け取る
        userInfo = (UserInfo)getIntent().getSerializableExtra("userInfo");

        //Json →　デシリアライズ
        String jsonStr = "[{\"cacthCopy\":\"MAHOT COFFEEの想いが詰まったブレンド\",\"ditail\":\"しっかりとしたビターなコクがあり、赤ワインのような上品なボディー。アクセントに完熟したチェリーのような風味がアフターテイストで楽しめます。,●●●●○,●●○○○,●●○○○,\\\\700\\/100ｇ　\\\\1300\\/200ｇ,\\\\370,\\\\370\",\"productName\":\"MAHOT ブレンド　SONE\"},{\"cacthCopy\":\"当店の新提案。「甘み」を楽しめるコーヒー\",\"ditail\":\"ハチミツのような甘味と紅茶のような風味、やわらかい酸味は、爽やかな朝を演出する一杯にふさわしいコーヒー,●●●○○,●●●○○,●●●●○,\\\\750\\/100ｇ　\\\\1400\\/200ｇ,\\\\370,\\\\370\",\"productName\":\"MAHOT ブレンド　SWEET\"},{\"cacthCopy\":\"焙煎機で焼き上げた特製のミックスナッツ\",\"ditail\":\"この焙煎機の特徴でもある排気の調整によりまるで燻製ナッツのような、ほのかに香るコーヒーの香りをお楽しみいただけます。またナッツとコーヒー豆を一緒に召し上がっていただくと香りの相乗効果でより一層お楽しみいただけます。\\u000a,-,-,-,\\\\500\\/80ｇ,-,-\",\"productName\":\"ミックスナッツ in コーヒー豆\"}]";
        try {
            sampleDataList = objectMapper.readValue(jsonStr, new TypeReference<List<ProductInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        String commentjsonStr = "{\"commentDataList\":[{\"insertDate\":\"2017.11.25\",\"sex\":0,\"star\":5,\"userCmt\":\"美味しかった\",\"userId\":1,\"userName\":\"小田\"},{\"insertDate\":\"2017.12.1\",\"sex\":0,\"star\":3,\"userCmt\":\"苦かった\",\"userId\":2,\"userName\":\"田中\"},{\"insertDate\":\"2017.12.5\",\"sex\":0,\"star\":2,\"userCmt\":\"いまいち\",\"userId\":3,\"userName\":\"中橋\"},{\"insertDate\":\"2017.12.4\",\"sex\":0,\"star\":4,\"userCmt\":\"また来ます\",\"userId\":4,\"userName\":\"中野\"},{\"insertDate\":\"2017.12.5\",\"sex\":1,\"star\":1,\"userCmt\":\"まぁまぁ\",\"userId\":5,\"userName\":\"溝辺\"}],\"offset\":0,\"totalNumber\":5}";
        try {
            commentData = objectMapper.readValue(commentjsonStr,CommentJson.class);

        }catch (Exception e){

        }


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
