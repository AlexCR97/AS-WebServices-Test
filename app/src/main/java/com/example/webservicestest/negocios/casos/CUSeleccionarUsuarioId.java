package com.example.webservicestest.negocios.casos;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.webservicestest.entidades.Usuario;
import com.example.webservicestest.negocios.presentadores.PresentadorUsuario;
import com.example.webservicestest.servicios.web.ServicioWeb;
import com.example.webservicestest.servicios.web.implementaciones.SWSeleccionarUsuarioId;

import org.json.JSONObject;

public class CUSeleccionarUsuarioId extends CasoUso<Usuario> {

    public CUSeleccionarUsuarioId(Context context, EventoPeticionAceptada<Usuario> eventoPeticionAceptada, EventoPeticionRechazada eventoPeticionRechazada) {
        super(context, eventoPeticionAceptada, eventoPeticionRechazada);
    }

    @Override
    protected ServicioWeb definirServicioWeb() {
        return new SWSeleccionarUsuarioId(context, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                PresentadorUsuario presentadorUsuario = new PresentadorUsuario();
                Usuario usuario = presentadorUsuario.procesar(response);

                eventoPeticionAceptada.alAceptarPeticion(usuario);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                eventoPeticionRechazada.alRechazarOperacion();
            }
        });
    }

}
