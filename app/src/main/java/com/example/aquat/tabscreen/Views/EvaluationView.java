package com.example.aquat.tabscreen.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.aquat.tabscreen.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aquat on 2017/12/24.
 */

public class EvaluationView extends LinearLayout {

    protected List<StarView> starButtons;
    private CallBackTask callBackTask;

    public EvaluationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View layout = LayoutInflater.from(context).inflate(R.layout.star_layout2,this);


        starButtons = new ArrayList<StarView>();
        for(int i = 0;i < StarInfo.getStarCount();i++){
            StarView starButton = (StarView) layout.findViewById((Integer) StarInfo.getStarItem(i));
            starButton.setIndex(i);
            starButton.setOnCallBack(new StarView.CallBackTask() {
                @Override
                public void CallBack(int result) {
                    //スターボタンのコールバック
                    changeStarState(result);


                }
            });

            starButtons.add(starButton);
        }
    }

    public void setOnCallBack(CallBackTask _cbj){
        callBackTask = _cbj;
    }

    //コールバックの抽象クラス
    public static abstract class CallBackTask{
        abstract public void CallBack(int result);
    }

    public void changeStarState(int index)
    {
        try{
            //index位置までONに、以降はOFFにする
            for(int i = 0;i < starButtons.size();i++){
                if(i<=index){
                    starButtons.get(i).setState(StarView.StarState.ON);
                }else{
                    starButtons.get(i).setState(StarView.StarState.OFF);
                }
            }
            //タップ位置をコールバックする
            callBackTask.CallBack(index);
        }catch (Exception e){

        }

    }

    public static class StarInfo extends BaseAdapter{

        private static Integer[] starIdArray ={
          R.id.star_1,
                R.id.star_2,
                R.id.star_3,
                R.id.star_4,
                R.id.star_5,
        };

        @Override
        public int getCount() {
            return starIdArray.length;
        }

        @Override
        public Object getItem(int i) {
            return starIdArray[i];
        }

        public static int getStarCount() {
            return starIdArray.length;
        }

        public static Object getStarItem(int i) {
            return starIdArray[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }
}
