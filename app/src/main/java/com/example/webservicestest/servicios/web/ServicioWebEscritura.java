package com.example.webservicestest.servicios.web;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public abstract class ServicioWebEscritura extends ServicioWeb<String> {

    public ServicioWebEscritura(Context context, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(context, responseListener, errorListener);
    }

    protected abstract Map<String, String> definirParams(Object... args);

    @Override
    protected Request definirRequest(String url, final Object... args) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                return definirParams(args);
            }
        };
        stringRequest.setRetryPolicy(defaultRetryPolicy);
        return stringRequest;
    }

}
