package com.example.webservicestest.servicios.implementaciones;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Response;
import com.example.webservicestest.servicios.ServicioWebImagen;

public class SWImagenUsuario extends ServicioWebImagen {

    public SWImagenUsuario(Context context, Response.Listener<Bitmap> responseListener, Response.ErrorListener errorListener) {
        super(context, responseListener, errorListener);
    }

    @Override
    protected String definirUrl(Object... args) {
        String imagePath = args[0].toString();
        return "http://192.168.0.2/web_services_test/" + imagePath;
    }

}
