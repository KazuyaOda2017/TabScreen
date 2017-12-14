package com.example.aquat.tabscreen;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aquat on 2017/12/10.
 */

final class UserInfo implements Serializable{

    //シングルトンインスタンス
    private static UserInfo instance = new UserInfo();

    //region プロパティ
    private String userName;
    public String getUserName(){return  userName;}
    public void setUserName(String userName){this.userName = userName;}

    private int userId;
    public int getUserId(){return userId;}
    public void setUserId(int userId){this.userId = userId;}

    private int sex;
    public int getSex(){return sex;}
    public void setSex(int i){this.sex = i;}


    private HashMap<String,String> productInfoMap;
    public HashMap<String,String> getProductInfoMap(){return this.productInfoMap;}
    public void addProductInfo(String key,String value){
        this.productInfoMap.put(key,value);
    }
    //endregion

    //region 定数
    public static String PREKEY_USERNAME = "userName";

    public static String PREKRY_USERID = "userId";

    public static String PREKEY_SEX = "sex";
    //endregion

    //region コンストラクタ
    private UserInfo(){
        this.productInfoMap = new HashMap<String,String>();
    }

    public static synchronized UserInfo getInstance(){
        if(instance==null){
            instance = new UserInfo();
        }
        return instance;
    }
    //endregion

    //region コンバーター
    public boolean ConvertProductInfo(ProductInfo productInfo){

        try{

            String[] indexlist = productInfo.indexInfo.split(",",0);

            //商品名
            addProductInfo(indexlist[0],productInfo.contentsName);

            //キャッチコピー
            addProductInfo(indexlist[1],productInfo.description);

            //リストの３列目からは詳細データ
            //詳細
            String[] values = productInfo.delInfo.split(",",-1);
            //項目分ループする
            for(int i = 2,j = 0; i < indexlist.length;i++,j++){
                addProductInfo(indexlist[i],values[j]);
            }


            return true;
        }catch (Exception e){
            return false;
        }
    }
    //endregion

}
