package com.example.aquat.tabscreen.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.aquat.tabscreen.ProductInfo;
import com.example.aquat.tabscreen.R;

import java.util.HashMap;

/**
 * Created by aquat on 2017/12/25.
 */

public class ContentsCardView extends LinearLayout {

    //region 変数
    private TableLayout tableLayout;
    //endregion

    /**
     * コンストラクタ
     * @param context
     * @param attrs
     */
    public ContentsCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //レイアウトを取得する
        View layout = LayoutInflater.from(context).inflate(R.layout.product_card,this);

        //TableLayoutの取得
        tableLayout = (TableLayout)layout.findViewById(R.id.product_table);


    }

    /**
     * 商品情報をテーブルにセットする
     * @param productInfo
     */
    public void setContentsInfo(HashMap<String,String> productInfo,Context context){
        try {
            //TableLayoutをViewGropに設定
            ViewGroup vg = (ViewGroup)tableLayout;
            
            //商品名　キャッチコピー　販売価格　ドリップコーヒー価格を表示する
            int row = 0;
            for(String index: ProductInfo.cardIndexNames) {
                LayoutInflater.from(context).inflate(R.layout.table_row_layout, vg);
                TableRow tr = (TableRow) vg.getChildAt(row);

                TextView indexView = (TextView) tr.getChildAt(0);
                TextView valueView = (TextView)tr.getChildAt(1);

                indexView.setText(index);
                valueView.setText(productInfo.get(index));
                //一行目はマージンを調整
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
                row ++ ;

            }

        }catch (Exception e){

        }
    }
}
