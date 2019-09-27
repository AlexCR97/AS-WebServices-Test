package com.example.webservicestest.external.web;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

public abstract class WebServiceRead<T> extends WebService<T> {

    protected WebServiceRead(Context context, RequestAcceptedListener<T> requestAcceptedListener, RequestRejectedListener requestRejectedListener) {
        super(context, requestAcceptedListener, requestRejectedListener);
    }

    @Override
    protected Request defineRequest(String url, Object... params) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, defineResponseListener(), defineErrorListener());
        jsonObjectRequest.setRetryPolicy(defaultRetryPolicy);
        return jsonObjectRequest;
    }

}
