package com.example.aquat.tabscreen.Views;

import android.content.Context;
import android.media.Image;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.aquat.tabscreen.R;

/**
 * Created by aquat on 2017/12/24.
 */

public class StarView extends LinearLayout {

    final ImageButton imageButton;
    private CallBackTask callBackTask;

    public enum StarState{
        ON,
        OFF,
    }

    private StarState state;
    public StarState getState(){return this.state;}
    public void setState(StarState state){
        if(state == StarState.OFF){
            imageButton.setImageResource(R.drawable.star_off);
        }else {
            imageButton.setImageResource(R.drawable.star_on);
        }
        this.state = state;

    }

    private int index;
    public int getIndex(){return this.index;}
    public void setIndex(int index){this.index = index;}

    public StarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View layout = LayoutInflater.from(context).inflate(R.layout.star_base_layout,this);
        imageButton = (ImageButton)layout.findViewById(R.id.star);

        //画像初期化
        setState(StarState.OFF);

        //インデックス初期化
        setIndex(0);

        final View.OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                //indexをコールバックする
                callBackTask.CallBack(getIndex());
            }
        };

        //リスナーを登録
        imageButton.setOnClickListener(onClickListener);
    }

    public void setOnCallBack(CallBackTask _cbj){
        callBackTask = _cbj;
    }

    //コールバックの抽象クラス
    public static abstract class CallBackTask{
        abstract public void CallBack(int result);
    }
}
