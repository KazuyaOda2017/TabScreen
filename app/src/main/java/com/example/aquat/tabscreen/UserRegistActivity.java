package com.example.aquat.tabscreen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by aquat on 2017/12/11.
 */

public class UserRegistActivity extends Activity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private  UserInfo userInfo;

    private EditText editText;
    private RadioGroup radioGroup;
    private Button submitBtn;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_regist_layout);

        userInfo = UserInfo.getInstance();

        //プリファレンスの読み込み
        try {
            preferences = getSharedPreferences("UserInfo",MODE_WORLD_READABLE|MODE_WORLD_WRITEABLE);

            //エディターを設定
            editor = preferences.edit();

            //ユーザー情報の読み込み
            userInfo.setUserName(preferences.getString(UserInfo.PREKEY_USERNAME,""));
            userInfo.setUserId(preferences.getInt(UserInfo.PREKRY_USERID,0));
            userInfo.setSex(preferences.getInt(UserInfo.PREKEY_SEX,0));

        }catch (Exception e){

        }

        //テスト用　商品データの受け取り
        //Json →　デシリアライズ
        ProductInfo productInfo = new ProductInfo();
        String jsonStr = "{\"cacthCopy\":\"MAHOT COFFEEの想いが詰まったブレンド\",\"ditail\":\"しっかりとしたビターなコクがあり、赤ワインのような上品なボディー。アクセントに完熟したチェリーのような風味がアフターテイストで楽しめます。,●●●●○,●●○○○,●●○○○,700円\\/100ｇ　1300円\\/200ｇ,370円,370円\",\"productName\":\"MAHOT ブレンド　SONE\"}";
        try {
            productInfo = objectMapper.readValue(jsonStr, ProductInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //項目名を取得
        productInfo.indexInfo = "商品名,キャッチコピー,詳細,コク,甘味,酸味,販売価格,ドリップコーヒー価格,カフェオレ価格";

        userInfo.ConvertProductInfo(productInfo);


        if(userInfo.getUserId() != 0){
            //画面遷移
            Intent intent = new Intent(getApplication(),TabMainActivity.class);
            intent.putExtra("userInfo",  userInfo);
            startActivity(intent);
            UserRegistActivity.this.finish();//この画面を終了する
        }

        //エディットテキストの設定
        editText = (EditText)findViewById(R.id.edittext_username);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //変更されたテキストを表示
                String inputStr = editable.toString();

            }
        });


        //ラジオボタンの設定
        radioGroup = (RadioGroup)findViewById(R.id.radiogroup_sex);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                try {
                    RadioButton rb = (RadioButton)findViewById(i);
                    int sex = (int)rb.getTag();

                    //ユーザー情報更新
                    userInfo.setSex(sex);

                }catch (Exception e){

                }
            }
        });

        //初期-男にチェックを入れる
        RadioButton rb = (RadioButton)findViewById(R.id.radiobtn_man);
        rb.setChecked(true);


        //送信ボタンの設定
        submitBtn = (Button)findViewById(R.id.submit_userInfoBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //入力テキストをユーザー情報に登録
                String userName = editText.getText().toString();
                userInfo.setUserName(userName);

                //ユーザー名と性別をサーバーに送信

                //ユーザーIDを取得
                userInfo.setUserId(1);

                try {
                    //プリファレンスに書き込み
                    editor.putString(UserInfo.PREKEY_USERNAME,userInfo.getUserName());
                    editor.putInt(UserInfo.PREKRY_USERID,userInfo.getUserId());
                    editor.putInt(UserInfo.PREKEY_SEX,userInfo.getSex());

                    editor.commit();

                }catch (Exception e){
                }

                //ユーザー情報を渡して画面遷移する
                Intent intent = new Intent(getApplication(),TabMainActivity.class);
               intent.putExtra("userInfo",  userInfo);
               startActivity(intent);
               UserRegistActivity.this.finish();//この画面を終了する
            }
        });

    }

}
