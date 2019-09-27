package com.example.webservicestest.external.web;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.webservicestest.domain.entities.User;

import java.util.HashMap;
import java.util.Map;

public class WSInsertUser extends WebServiceWrite {

    public WSInsertUser(Context context, RequestAcceptedListener<Void> requestAcceptedListener, RequestRejectedListener requestRejectedListener) {
        super(context, requestAcceptedListener, requestRejectedListener);
    }

    @Override
    protected Map<String, String> defineParams(Object... args) {
        User user = (User) args[0];
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(user.getId()));
        params.put("name", user.getName());
        params.put("last_name", user.getLastName());
        params.put("birth_date", user.getBirthDate());
        params.put("image", user.getImage());
        return params;
    }

    @Override
    protected String defineUrl(Object... params) {
        return "http://192.168.0.2/web_services_test/WSInsertUserProcPost.php";
    }

    @Override
    protected Response.Listener defineResponseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equalsIgnoreCase("successful")) {
                    requestAcceptedListener.onRequestAccepted(null);
                }
            }
        };
    }

    @Override
    protected Response.ErrorListener defineErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                requestRejectedListener.onRequestRejected();
            }
        };
    }

}
