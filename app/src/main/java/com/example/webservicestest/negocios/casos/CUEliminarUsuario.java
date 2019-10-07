package com.example.webservicestest.negocios.casos;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.webservicestest.servicios.ServicioWeb;
import com.example.webservicestest.servicios.implementaciones.SWEliminarUsuario;

public class CUEliminarUsuario extends CasoUso<String> {

    public CUEliminarUsuario(Context context, EventoPeticionAceptada<String> eventoPeticionAceptada, EventoPeticionRechazada eventoPeticionRechazada) {
        super(context, eventoPeticionAceptada, eventoPeticionRechazada);
    }

    @Override
    protected ServicioWeb definirServicioWeb() {
        return new SWEliminarUsuario(context, new Response.Listener<String>() {
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
