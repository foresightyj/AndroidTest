package com.fht360.com.helloandroid;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/26.
 */

public class PostModel {
    public int userId;
    public int id;
    public String title;
    @SerializedName("body")
    public String content;
}
