package com.example.webservicestest.negocios.casos;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.webservicestest.servicios.web.ServicioWeb;
import com.example.webservicestest.servicios.web.implementaciones.SWRegistrarUsuario;

public class CURegistrarUsuario extends CasoUso<String> {

    public CURegistrarUsuario(Context context, EventoPeticionAceptada<String> eventoPeticionAceptada, EventoPeticionRechazada eventoPeticionRechazada) {
        super(context, eventoPeticionAceptada, eventoPeticionRechazada);
    }

    @Override
    protected ServicioWeb definirServicioWeb() {
        return new SWRegistrarUsuario(context, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                eventoPeticionAceptada.alAceptarPeticion(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                eventoPeticionRechazada.alRechazarOperacion();
            }
        });
    }
}
