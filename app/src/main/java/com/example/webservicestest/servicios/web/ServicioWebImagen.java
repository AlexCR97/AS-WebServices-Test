package com.example.webservicestest.servicios.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

public abstract class ServicioWebImagen extends ServicioWeb<Bitmap> {

    public ServicioWebImagen(Context context, Response.Listener<Bitmap> responseListener, Response.ErrorListener errorListener) {
        super(context, responseListener, errorListener);
    }

    @Override
    protected Request definirRequest(String url, Object... args) {
        ImageRequest imageRequest = new ImageRequest(
                url,
                responseListener,
                0, 0,
                ImageView.ScaleType.CENTER,
                null,
                errorListener
        );
        imageRequest.setRetryPolicy(defaultRetryPolicy);

        return imageRequest;
    }
}
