package com.example.webservicestest.servicios.web.implementaciones;

import android.content.Context;

import com.android.volley.Response;
import com.example.webservicestest.servicios.web.ServicioWebEscritura;
import com.example.webservicestest.servicios.web.Servidor;

import java.util.HashMap;
import java.util.Map;

public class SWEliminarUsuario extends ServicioWebEscritura {

    public SWEliminarUsuario(Context context, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(context, responseListener, errorListener);
    }

    @Override
    protected Map<String, String> definirParams(Object... params) {
        int id = Integer.parseInt(params[0].toString());
        Map<String, String> args = new HashMap<>();
        args.put("id", String.valueOf(id));
        return args;
    }

    @Override
    protected String definirUrl(Object... args) {
        return "http://" + Servidor.IP + "/web_services_test/WSDeleteUser.php";
    }

}
