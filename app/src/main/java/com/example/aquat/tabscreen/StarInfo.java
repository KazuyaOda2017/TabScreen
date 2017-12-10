package com.example.aquat.tabscreen;

/**
 * Created by aquat on 2017/12/10.
 */

public class StarInfo {

    private int posision;
    public int getPosision(){return posision;}
    public void setPosision(int i){this.posision = i;}

    private boolean isChecked;
    public boolean getIsChecked(){return isChecked;}
    public void setChecked(boolean value){this.isChecked = value;}

    public StarInfo(){

    }

    public StarInfo(int posision,boolean value){
        this.posision = posision;
        this.isChecked = value;
    }

}
