package com.example.webservicestest.servicios;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;

public abstract class ServicioWeb<T> {

    private Context context;
    protected Response.Listener<T> responseListener;
    protected Response.ErrorListener errorListener;

    protected final DefaultRetryPolicy defaultRetryPolicy = new DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
    );

    public ServicioWeb(Context context, Response.Listener<T> responseListener, Response.ErrorListener errorListener) {
        this.context = context;
        this.responseListener = responseListener;
        this.errorListener = errorListener;
    }

    protected abstract String definirUrl(Object... args);

    protected abstract Request definirRequest(String url, Object... args);

    public void enviarPeticion(Object... args) {
        String url = definirUrl(args);
        url = url.replace(" ", "%20");
        Request request = definirRequest(url, args);
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

}
