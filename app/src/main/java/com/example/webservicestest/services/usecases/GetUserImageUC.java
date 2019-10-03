package com.example.webservicestest.services.usecases;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.webservicestest.services.WebServiceImage;

public class GetUserImageUC extends WebServiceImage {

    public GetUserImageUC(Context context, RequestAcceptedListener<Bitmap> requestAcceptedListener, RequestRejectedListener requestRejectedListener) {
        super(context, requestAcceptedListener, requestRejectedListener);
    }

    @Override
    protected String defineUrl(Object... params) {
        String imagePath = params[0].toString();
        return "http://192.168.0.2/web_services_test/" + imagePath;
    }

    @Override
    protected Response.Listener<Bitmap> defineResponseListener() {
        return new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                Log.d("JSON", "GetUserImageIC request accepted! Response: " + response.toString());
                requestAcceptedListener.onRequestAccepted(response);
            }
        };
    }

    @Override
    protected Response.ErrorListener defineErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON", "GetUserImageIC request rejected. Error: " + error.getMessage());
                requestRejectedListener.onRequestRejected();
            }
        };
    }
}
