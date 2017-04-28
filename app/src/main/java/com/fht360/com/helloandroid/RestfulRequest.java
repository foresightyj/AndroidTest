package com.fht360.com.helloandroid;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import static com.android.volley.toolbox.HttpHeaderParser.parseCacheHeaders;
import static com.android.volley.toolbox.HttpHeaderParser.parseCharset;

/**
 * Created by yuanjian on 2017/4/26.
 */

public class RestfulRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Response.Listener<T> listener;
    private final Class<T> clazz;

    public RestfulRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(url, errorListener);
        this.listener = listener;
        this.clazz = clazz;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, parseCharset(response.headers));
            //see http://stackoverflow.com/questions/5554217/google-gson-deserialize-listclass-object-generic-type
            return Response.success(gson.fromJson(json, clazz), parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
