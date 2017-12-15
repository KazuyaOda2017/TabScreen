package com.example.aquat.tabscreen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.TableRow;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    protected ViewGroup vg_productCard;

    protected List<ImageButton> starBtnList;

    View layout;

    protected int selectStar;

    private SwipeRefreshLayout mSwipeRefresh;

    //ユーザー情報
    public UserInfo userInfo;
    //コメントデータ
    public CommentJson commentData;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        //レイアウトを取得
        layout = inflater.inflate(R.layout.fragment1, null);

        //ユーザー情報の作成
       /* userInfo = new UserInfo();
        userInfo.setUserId(1);
        userInfo.setUserName("K.Oda");*/

        //アダプターの作成
        adapter = new CommentListAdapter(getContext(),userInfo);

        //コメントデータの取得
        getCommentData();
        //取得コメントを表示する
        setCmtDataTimeLine(this.commentData);

        //region 商品情報カード作成
        try {
            vg_productCard = (ViewGroup)layout.findViewById(R.id.product_table);
            //VGにテーブルロウを追加していく
            HashMap<String,String> productInfoMap = userInfo.getProductInfoMap();
            //商品名　キャッチコピー　販売価格　ドリップコーヒー価格を表示する
            int row = 0;
            for(String index:ProductInfo.cardIndexNames){

                getLayoutInflater().inflate(R.layout.table_row_layout,vg_productCard);
                TableRow tr = (TableRow)vg_productCard.getChildAt(row);

                TextView indexView = ((TextView)(tr.getChildAt(0)));
                TextView valueView =  ((TextView)(tr.getChildAt(1)));
                indexView.setText(index);
                valueView.setText(productInfoMap.get(index));
                if(row == 0){
                    ViewGroup.LayoutParams lp = indexView.getLayoutParams();
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams)lp;
                    mlp.setMargins(1,1,1,1);
                    indexView.setLayoutParams(mlp);

                    lp = valueView.getLayoutParams();
                    mlp = (ViewGroup.MarginLayoutParams)lp;
                    mlp.setMargins(0,1,1,1);
                    valueView.setLayoutParams(mlp);

                }
                row++;
            }


        }catch (Exception e){

        }
        //endregion

        //region ListViewの設定
        //ListView生成
        listView = (ListView)layout.findViewById(R.id.comment_list);
        //境界線をなくす
        listView.setDivider(null);
        listView.setPadding(10,10,10,10);
        //アダプターをセット
        listView.setAdapter(adapter);
        //endregion

        //region SwipeRefreshの設定
        mSwipeRefresh = (SwipeRefreshLayout)layout.findViewById(R.id.swipe_refresh) ;
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 引っ張って離した時に呼ばれます。
                //loadData();
                ReloadCommentData();



            }
        });
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

        //region 送信ボタン
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
               cInfo.userCmt = (inputStr);
                cInfo.star = (selectStar);
                cInfo.userName = userInfo.getUserName();
                cInfo.userId = userInfo.getUserId();
                cInfo.sex = userInfo.getSex();
                //日付を取得
                Date date = new Date();
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                String date1 = sdf1.format(date);
                cInfo.insertDate = (date1);

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

    //region 評価ボタン入れ替え処理
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
    //endregion

    //region テキスト入力チェック
    private boolean checkInputText(String str) {
        boolean result = true;
        if(str.trim().equals("")){
            result = false;
        }
        return result;
    }
    //endregion

    //region コメントデータ取得処理
    private boolean getCommentData(){

        try{

            //コメントデータを取得してフィールドに投げる
            String commentjsonStr = "{\"dispList\":[{\"insertDate\":\"2017.11.25\",\"sex\":0,\"star\":5,\"userCmt\":\"美味しかった\",\"userId\":1,\"userName\":\"小田\"},{\"insertDate\":\"2017.12.1\",\"sex\":0,\"star\":3,\"userCmt\":\"苦かった\",\"userId\":2,\"userName\":\"田中\"},{\"insertDate\":\"2017.12.5\",\"sex\":0,\"star\":2,\"userCmt\":\"いまいち\",\"userId\":3,\"userName\":\"中橋\"},{\"insertDate\":\"2017.12.4\",\"sex\":0,\"star\":4,\"userCmt\":\"また来ます\",\"userId\":4,\"userName\":\"中野\"},{\"insertDate\":\"2017.12.5\",\"sex\":1,\"star\":1,\"userCmt\":\"まぁまぁ\",\"userId\":5,\"userName\":\"溝辺\"}],\"offset\":0,\"totalNumber\":5}";
            try {
                commentData = objectMapper.readValue(commentjsonStr,CommentJson.class);

            }catch (Exception e){

            }


            return true;
        }catch (Exception e){
            return false;
        }


    }
    //endregion

    //region コメント表示処理
    private void setCmtDataTimeLine(CommentJson commentData){

        //コメントの数だけアダプターに追加
        for(CommentInfo info:commentData.dispList){
            adapter.add(info);
        }

    }
    //endregion

    //region コメントデータの追加読み込み
    private boolean ReloadCommentData() {

        try {

            //追加取得のデータ
            String commentjsonStr = "{\"dispList\":[{\"insertDate\":\"2017.11.25\",\"sex\":0,\"star\":5,\"userCmt\":\"最高\",\"userId\":6,\"userName\":\"小田\"},{\"insertDate\":\"2017.12.1\",\"sex\":0,\"star\":3,\"userCmt\":\"苦かった\",\"userId\":2,\"userName\":\"田中\"},{\"insertDate\":\"2017.12.5\",\"sex\":0,\"star\":2,\"userCmt\":\"いまいち\",\"userId\":3,\"userName\":\"中橋\"},{\"insertDate\":\"2017.12.4\",\"sex\":0,\"star\":4,\"userCmt\":\"また来ます\",\"userId\":4,\"userName\":\"中野\"},{\"insertDate\":\"2017.12.5\",\"sex\":1,\"star\":1,\"userCmt\":\"まぁまぁ\",\"userId\":5,\"userName\":\"溝辺\"}],\"offset\":0,\"totalNumber\":5}";

                CommentJson cData = new CommentJson();
                cData = objectMapper.readValue(commentjsonStr, CommentJson.class);


                //adapter.addAll(commentData.dispList);
                //上にインサート
                int index = cData.dispList.size() - 1;
                for (int i = index; i >= 0; i--) {
                    adapter.insert(cData.dispList.get(i), 0);
                }

                //setCmtDataTimeLine(cData);

                if (mSwipeRefresh.isRefreshing()) {
                    mSwipeRefresh.setRefreshing(false);
                }

            return true;
        } catch (Exception e) {
            if (mSwipeRefresh.isRefreshing()) {
                mSwipeRefresh.setRefreshing(false);
            }
            return false;
        }


    }
    //endregion


}
