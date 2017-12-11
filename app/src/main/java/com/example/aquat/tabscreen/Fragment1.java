package com.example.aquat.tabscreen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aquat on 2017/12/06.
 */

public class Fragment1 extends Fragment {
    protected ListView listView;
    protected EditText input_text;
    protected ImageButton submitBtn;
    protected  CommentListAdapter adapter;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

    protected List<ImageButton> starBtnList;

    View layout;

    protected int selectStar;

    protected UserInfo userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        //レイアウトを取得
        layout = inflater.inflate(R.layout.fragment1, null);

        //ユーザー情報の作成
        userInfo = new UserInfo();
        userInfo.setUserId(1);
        userInfo.setUserName("K.Oda");

        //アダプターの作成
        adapter = new CommentListAdapter(getContext(),userInfo);

        //region ListViewの設定
        //ListView生成
        listView = (ListView)layout.findViewById(R.id.comment_list);
        //境界線をなくす
        listView.setDivider(null);
        listView.setPadding(10,10,10,10);
        //アダプターをセット
        listView.setAdapter(adapter);
        //endregion

        //コメント入力エリアの設定
        //region エディットテキスト

        input_text = (EditText)layout.findViewById(R.id.edittext);
        input_text.addTextChangedListener(new TextWatcher() {
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

                if(inputStr.trim().equals("")){
                    submitBtn.setEnabled(false);
                }else{
                    submitBtn.setEnabled(true);
                }
            }
        });
        //endregion

        //region　送信ボタン
        submitBtn = (ImageButton)layout.findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //テキストボックスの入力をコメントに反映する
                String inputStr = input_text.getText().toString();

                //入力をチェックする
                if(!checkInputText(inputStr)) return;

                //コメント情報を作成
                CommentInfo cInfo = new CommentInfo();
                cInfo.setUserCmt(inputStr);
                cInfo.setStar(selectStar);
                cInfo.setUserName("K.Oda");
                cInfo.setUserID(1);
                //日付を取得
                Date date = new Date();
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                String date1 = sdf1.format(date);
                cInfo.setInsertDate(date1);

                //コメントを追加する
                adapter.add(cInfo);

                //スクロールを一番下へ
               int itemCount = listView.getCount();
               listView.setSelection(itemCount -1);

               //入力を空にする
                input_text.setText("");

                //テキストボックスからフォーカスを外す
                layout.requestFocus();
            }
        });

        if(input_text.getText().toString().trim().equals("")) {

            //送信ボタンを非表示
            submitBtn.setEnabled(false);
        }
        //endregion

        //region 評価ボタン
        //リストを生成
        starBtnList = new ArrayList<>();
        //ViewGroup を取得
        ViewGroup vg = (ViewGroup)layout.findViewById(R.id.stars);
        for(int i = 0; i < 5;i++){

            //初期表示-
            //評価ボタン情報を作成
            StarInfo sInfo = new StarInfo(i+1,false);

            //ImageButtonを取得
            ImageButton imageButton = (ImageButton)vg.getChildAt(i);
            imageButton.setTag(sInfo);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StarInfo sInfo = (StarInfo)view.getTag();

                    ImageButton iBtn = (ImageButton)view;
                    //評価ボタンの画像入れ替え
                    ChangeStarImage(sInfo.getPosision());
                }
            });

            starBtnList.add(imageButton);
        }

        //評価ボタンの初期表示-評価３にする
        int star = 3;
        ChangeStarImage(star);

        //endregion

        layout.requestFocus();

        return layout;

    }

    private void ChangeStarImage(int posision) {

        for(int i = 0; i < starBtnList.size();i++){

            ImageButton iBtn = starBtnList.get(i);
            StarInfo sInfo = (StarInfo)iBtn.getTag();

            //タップ位置までonにする
            if(i < posision){
                iBtn.setImageResource(R.drawable.star_on);
                sInfo.setChecked(true);
            }else{
                //タップ位置より後ろをoffにする
                iBtn.setImageResource(R.drawable.star_off);
                sInfo.setChecked(false);
            }

            //選択をフィールドになげる
            selectStar = posision;

            //評価情報を更新する
            iBtn.setTag(sInfo);
        }

        // if(posision == 5) return;

    }

    private boolean checkInputText(String str) {
        boolean result = true;
        if(str.trim().equals("")){
            result = false;
        }
        return result;
    }
}
