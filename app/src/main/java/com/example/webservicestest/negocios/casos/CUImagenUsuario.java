package com.example.webservicestest.negocios.casos;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.webservicestest.servicios.ServicioWeb;
import com.example.webservicestest.servicios.implementaciones.SWImagenUsuario;

public class CUImagenUsuario extends CasoUso<Bitmap> {

    public CUImagenUsuario(Context context, EventoPeticionAceptada<Bitmap> eventoPeticionAceptada, EventoPeticionRechazada eventoPeticionRechazada) {
        super(context, eventoPeticionAceptada, eventoPeticionRechazada);
    }

    @Override
    public ServicioWeb definirServicioWeb() {
        return new SWImagenUsuario(context, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
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
