package com.example.webservicestest.servicios.web.implementaciones;

import android.content.Context;

import com.android.volley.Response;
import com.example.webservicestest.entidades.Usuario;
import com.example.webservicestest.servicios.web.ServicioWebEscritura;
import com.example.webservicestest.servicios.web.Servidor;

import java.util.HashMap;
import java.util.Map;

public class SWActualizarUsuario extends ServicioWebEscritura {

    public SWActualizarUsuario(Context context, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(context, responseListener, errorListener);
    }

    @Override
    protected Map<String, String> definirParams(Object... params) {
        int id = Integer.parseInt(params[0].toString());
        Usuario user = (Usuario) params[1];

        Map<String, String> args = new HashMap<>();
        args.put("id", String.valueOf(id));
        args.put("new_id", String.valueOf(user.getId()));
        args.put("name", user.getNombre());
        args.put("last_name", user.getApellido());
        args.put("birth_date", user.getFechaNacimiento());
        args.put("image", "dummy");

        return args;
    }

    @Override
    protected String definirUrl(Object... args) {
        return "http://" + Servidor.IP + "/web_services_test/WSUpdateUser.php";
    }

}
