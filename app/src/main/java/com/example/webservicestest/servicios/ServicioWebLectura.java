package com.example.webservicestest.servicios;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public abstract class ServicioWebLectura extends ServicioWeb<JSONObject> {

    public ServicioWebLectura(Context context, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        super(context, responseListener, errorListener);
    }

    @Override
    protected Request definirRequest(String url, Object... args) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, responseListener, errorListener);
        jsonObjectRequest.setRetryPolicy(defaultRetryPolicy);
        return jsonObjectRequest;
    }

}
