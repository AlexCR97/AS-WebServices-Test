package com.example.webservicestest.negocios.casos;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.webservicestest.entidades.Usuario;
import com.example.webservicestest.negocios.presentadores.PresentadorListaUsuarios;
import com.example.webservicestest.servicios.ServicioWeb;
import com.example.webservicestest.servicios.implementaciones.SWListarUsuarios;

import org.json.JSONObject;

import java.util.List;

public class CUListarUsuarios extends CasoUso<List<Usuario>> {

    public CUListarUsuarios(Context context, EventoPeticionAceptada<List<Usuario>> eventoPeticionAceptada, EventoPeticionRechazada eventoPeticionRechazada) {
        super(context, eventoPeticionAceptada, eventoPeticionRechazada);
    }

    @Override
    public ServicioWeb definirServicioWeb() {
        return new SWListarUsuarios(context, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                PresentadorListaUsuarios presentador = new PresentadorListaUsuarios();
                List<Usuario> usuarios = presentador.procesar(response);
                eventoPeticionAceptada.alAceptarPeticion(usuarios);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                eventoPeticionRechazada.alRechazarOperacion();
            }
        });
    }
}
