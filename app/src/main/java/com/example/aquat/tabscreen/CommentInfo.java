package com.example.aquat.tabscreen;

/**
 * Created by aquat on 2017/12/10.
 */

public class CommentInfo {


    //userID
    private int userID;
    public int getUserID(){return userID;}
    public void setUserID(int id){this.userID = id;}

    //ユーザー名
    private String userName;
    public String getUserName(){return userName;}
    public void setUserName(String userName){this.userName = userName;}

    //性別
    private int sex;
    public int getSex(){return sex;}
    public void setSex(int sex){this.sex = sex;}

    //コメント
    private String userCmt;
    public String getComment(){return userCmt;}
    public void setUserCmt(String cmt){this.userCmt = cmt;}

    //評価
    private int star;
    public int getStar(){return star;}
    public void setStar(int num){this.star = num;}

    //登録日
    private String insertDate;
    public String getInsertDate(){return insertDate;}
    public void setInsertDate(String date){this.insertDate = date;}

    //コンストラクタ
    public CommentInfo(){}
}
