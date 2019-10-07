package com.example.webservicestest.servicios.implementaciones;

import android.content.Context;

import com.android.volley.Response;
import com.example.webservicestest.servicios.ServicioWebLectura;

import org.json.JSONObject;

public class SWSeleccionarUsuarioId extends ServicioWebLectura {

    public SWSeleccionarUsuarioId(Context context, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        super(context, responseListener, errorListener);
    }

    @Override
    protected String definirUrl(Object... args) {
        int id = Integer.parseInt(args[0].toString());
        return "http://192.168.0.2/web_services_test/WSSelectUserId.php?id=" + id;
    }

}
