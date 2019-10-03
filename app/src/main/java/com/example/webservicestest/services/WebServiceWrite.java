package com.example.webservicestest.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public abstract class WebServiceWrite extends WebService<Boolean> {

    protected WebServiceWrite(Context context, RequestAcceptedListener<Boolean> requestAcceptedListener, RequestRejectedListener requestRejectedListener) {
        super(context, requestAcceptedListener, requestRejectedListener);
    }

    protected abstract Map<String, String> defineParams(Object... params);

    @Override
    protected Request defineRequest(String url, final Object... params) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, defineResponseListener(), defineErrorListener()) {
            @Override
            protected Map<String, String> getParams() {
                return defineParams(params);
            }
        };
        stringRequest.setRetryPolicy(defaultRetryPolicy);
        return stringRequest;
    }

}
