package com.fht360.com.helloandroid;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2017/4/27.
 */


public class PostService {
    private RequestQueue requestQueue;
    private final String APIROOT = "http://jsonplaceholder.typicode.com";
    private static String TAG = "PostService";

    private PostService(Context context)
    {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static PostService get(Context context)
    {
        return new PostService(context);
    }

    public void GetPosts(final Callback<PostModel[]> cb)
    {
        RestfulRequest<PostModel[]> req = new RestfulRequest<>(
                APIROOT + "/posts/",
                PostModel[].class,
                new Response.Listener<PostModel[]>() {
                    @Override
                    public void onResponse(PostModel[] posts) {
                        cb.onData(posts);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                    }
                }
        );
        requestQueue.add(req);
    }
}
