package com.fht360.com.helloandroid;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import static com.android.volley.toolbox.HttpHeaderParser.*;

/**
 * Created by yuanjian on 2017/4/26.
 */

public class RestfulRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private Class<T> clazz;
    private final Response.Listener<T> listener;

    @SuppressWarnings("unchecked")
    private void initClazz() {
        // the usual verbiage you already have in your question
        this.clazz = (Class<T>) (((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public RestfulRequest(String url, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(url, errorListener);
        this.listener = listener;
        initClazz();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, clazz),
                    parseCacheHeaders(response));
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
