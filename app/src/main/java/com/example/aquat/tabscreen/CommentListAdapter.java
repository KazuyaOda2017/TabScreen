package com.example.aquat.tabscreen;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aquat.tabscreen.Views.EvaluationView;

/**
 * Created by aquat on 2017/12/10.
 */

public class CommentListAdapter extends ArrayAdapter<CommentInfo> {

    LayoutInflater mInflater;
    protected UserInfo userInfo;

    public CommentListAdapter(@NonNull Context context,UserInfo userInfo) {
        super(context,0);
        mInflater = LayoutInflater.from(context);
        this.userInfo = userInfo;
    }

    //タップアクションを無効にする
    @Override
    public boolean isEnabled(int position){
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //コメント情報を取得
        CommentInfo info = getItem(position);

        //Viewが再利用可能かどうか
        if(convertView != null){

            int id = (int)convertView.getTag();
            if(id == info.userId) {

                //同じIDなら再利用
               // return  convertView;
            }

        }

        //レイアウトを取得
        //if(convertView == null){
            //自分のコメントかどうか
            int gravity = Gravity.RIGHT;
            if(info.userId != userInfo.getUserId()){
                convertView = mInflater.inflate(R.layout.cmt_other,parent,false);
                gravity = Gravity.LEFT;
            }else {
                convertView = mInflater.inflate(R.layout.com_self,parent,false);

            }

                if(info != null){

                    //コメント作成
                    TextView tv = (TextView)convertView.findViewById(R.id.cmt);
                    tv.setText(info.userCmt);
                    //評価を設定
                    EvaluationView evaluationView = (EvaluationView)convertView.findViewById(R.id.stars_mini);
                    evaluationView.setImageSize(15);
                    evaluationView.setGravity(gravity);
                    evaluationView.changeStarState(info.star - 1);
                    evaluationView.setMargin(0,0,0,0);

                   /* ViewGroup vg = (ViewGroup)convertView.findViewById(R.id.stars_mini);

                    //LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.starsmini_line2);
                    //linearLayout.setGravity(Gravity.RIGHT);
                    for(int i = 0;i< 5;i++){

                        ImageButton imageButton = (ImageButton)vg.getChildAt(i);

                        if(i < info.star) {
                            imageButton.setImageResource(R.drawable.star_on);
                        }else{
                            imageButton.setImageResource(R.drawable.star_off);
                        }
                    }*/
                    //ユーザー名
                    TextView userName = (TextView)convertView.findViewById(R.id.user_name);
                    userName.setText(info.userName);

                    //登録日
                    TextView insertDate = (TextView)convertView.findViewById(R.id.insert_date);
                    insertDate.setText(info.insertDate);

                    //タグにIDを登録する
                    convertView.setTag(info.userId);

                }



       //; }


        return convertView;
    }

}
