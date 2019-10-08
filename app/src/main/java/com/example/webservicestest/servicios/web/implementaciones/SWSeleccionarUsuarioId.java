package com.example.webservicestest.servicios.web.implementaciones;

import android.content.Context;

import com.android.volley.Response;
import com.example.webservicestest.servicios.web.ServicioWebLectura;
import com.example.webservicestest.servicios.web.Servidor;

import org.json.JSONObject;

public class SWSeleccionarUsuarioId extends ServicioWebLectura {

    public SWSeleccionarUsuarioId(Context context, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        super(context, responseListener, errorListener);
    }

    @Override
    protected String definirUrl(Object... args) {
        int id = Integer.parseInt(args[0].toString());
        return "http://" + Servidor.IP + "/web_services_test/WSSelectUserId.php?id=" + id;
    }

}
