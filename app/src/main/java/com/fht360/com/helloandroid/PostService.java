package com.fht360.com.helloandroid;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2017/4/27.
 */


public class PostService {
    private final String APIROOT = "http://jsonplaceholder.typicode.com";
    private static String TAG = "PostService";
    private Context mContext;
    private static PostService sInstance;
    private PostService(Context context)
    {
        mContext = context;
    }

    public static PostService getInstance(Context context)
    {
        if(sInstance == null){
            sInstance = new PostService(context);
        }
        return sInstance;
    }

    public void GetPosts(final VolleyCallback<PostModel[]> cb)
    {
        RestfulRequest<PostModel[]> req = new RestfulRequest<>(
                APIROOT + "/posts/",
                PostModel[].class,
                new Response.Listener<PostModel[]>() {
                    @Override
                    public void onResponse(PostModel[] posts) {
                        cb.onSuccess(posts);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                    }
                }
        );
        VolleyRequest.getInstance(mContext).addToRequestQueue(req);
    }
}
