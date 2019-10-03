package com.example.webservicestest.services.usecases;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.webservicestest.services.WebServiceWrite;

import java.util.HashMap;
import java.util.Map;

public class DeleteUserUC extends WebServiceWrite {

    public DeleteUserUC(Context context, RequestAcceptedListener<Boolean> requestAcceptedListener, RequestRejectedListener requestRejectedListener) {
        super(context, requestAcceptedListener, requestRejectedListener);
    }

    @Override
    protected Map<String, String> defineParams(Object... params) {
        int id = Integer.parseInt(params[0].toString());
        Map<String, String> args = new HashMap<>();
        args.put("id", String.valueOf(id));
        return args;
    }

    @Override
    protected String defineUrl(Object... params) {
        return "http://192.168.0.2/web_services_test/WSDeleteUser.php";
    }

    @Override
    protected Response.Listener defineResponseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("JSON", "Delete user request accepted: " + response);

                if (response.trim().equalsIgnoreCase("1"))
                    requestAcceptedListener.onRequestAccepted(true);
                else
                    requestAcceptedListener.onRequestAccepted(false);
            }
        };
    }

    @Override
    protected Response.ErrorListener defineErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON", "Error response: " + error.getMessage());
                requestRejectedListener.onRequestRejected();
            }
        };
    }
}
