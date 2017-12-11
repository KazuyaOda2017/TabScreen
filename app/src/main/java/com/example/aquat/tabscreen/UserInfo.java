package com.example.aquat.tabscreen;

import java.io.Serializable;

/**
 * Created by aquat on 2017/12/10.
 */

public class UserInfo implements Serializable{

    private String userName;
    public String getUserName(){return  userName;}
    public void setUserName(String userName){this.userName = userName;}

    private int userId;
    public int getUserId(){return userId;}
    public void setUserId(int userId){this.userId = userId;}

    private int sex;
    public int getSex(){return sex;}
    public void setSex(int i){this.sex = i;}

    //region 定数
    public static String PREKEY_USERNAME = "userName";

    public static String PREKRY_USERID = "userId";

    public static String PREKEY_SEX = "sex";
    //endregion

    //region コンストラクタ
    public UserInfo(){}
    //endregion

}
