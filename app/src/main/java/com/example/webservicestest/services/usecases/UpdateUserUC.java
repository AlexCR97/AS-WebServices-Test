package com.example.webservicestest.services.usecases;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.webservicestest.entities.User;
import com.example.webservicestest.services.WebServiceWrite;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserUC extends WebServiceWrite {

    public UpdateUserUC(Context context, RequestAcceptedListener<Boolean> requestAcceptedListener, RequestRejectedListener requestRejectedListener) {
        super(context, requestAcceptedListener, requestRejectedListener);
    }

    @Override
    protected Map<String, String> defineParams(Object... params) {
        int id = Integer.parseInt(params[0].toString());
        User user = (User) params[1];

        Map<String, String> args = new HashMap<>();
        args.put("id", String.valueOf(id));
        args.put("new_id", String.valueOf(user.getId()));
        args.put("name", user.getName());
        args.put("last_name", user.getLastName());
        args.put("birth_date", user.getBirthDate());
        args.put("image", "dummy");

        return args;
    }

    @Override
    protected String defineUrl(Object... params) {
        return "http://192.168.0.2/web_services_test/WSUpdateUser.php";
    }

    @Override
    protected Response.Listener defineResponseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("JSON", "Insert user request accepted: " + response);

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
