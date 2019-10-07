package com.example.webservicestest.servicios.implementaciones;

import android.content.Context;

import com.android.volley.Response;
import com.example.webservicestest.servicios.ServicioWebLectura;

import org.json.JSONObject;

public class SWListarUsuarios extends ServicioWebLectura {

    public SWListarUsuarios(Context context, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        super(context, responseListener, errorListener);
    }

    @Override
    protected String definirUrl(Object... args) {
        return "http://192.168.0.2/web_services_test/WSSelectUsers.php";
    }
}
