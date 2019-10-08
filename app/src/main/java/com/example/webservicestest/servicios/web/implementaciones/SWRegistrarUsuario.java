package com.example.webservicestest.servicios.web.implementaciones;

import android.content.Context;

import com.android.volley.Response;
import com.example.webservicestest.entidades.Usuario;
import com.example.webservicestest.servicios.web.ServicioWebEscritura;
import com.example.webservicestest.servicios.web.Servidor;

import java.util.HashMap;
import java.util.Map;

public class SWRegistrarUsuario extends ServicioWebEscritura {

    public SWRegistrarUsuario(Context context, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(context, responseListener, errorListener);
    }

    @Override
    protected Map<String, String> definirParams(Object... args) {
        Usuario user = (Usuario) args[0];
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(user.getId()));
        params.put("name", user.getNombre());
        params.put("last_name", user.getApellido());
        params.put("birth_date", user.getFechaNacimiento());
        params.put("image", user.getRutaImagen());
        return params;
    }

    @Override
    protected String definirUrl(Object... args) {
        return "http://" + Servidor.IP + "/web_services_test/WSInsertUserProcPost.php";
    }

}
