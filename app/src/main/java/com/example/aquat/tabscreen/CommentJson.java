package com.example.aquat.tabscreen;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aquat on 2017/12/11.
 */

public class CommentJson {

    public int totalNumber;

    public int offset;

    public List<CommentData> commentDataList;

    public CommentJson() {
        commentDataList = new ArrayList<CommentData>();

    }
}

class CommentData{

    public int userId;

    public String userName;

    public int sex;

    public String userCmt;

    public int star;

    public String insertDate;


}
