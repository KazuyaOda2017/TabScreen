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

    public List<CommentInfo> dispList;

    public CommentJson() {
        dispList = new ArrayList<CommentInfo>();

    }
}

