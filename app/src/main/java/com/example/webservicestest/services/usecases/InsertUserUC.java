package com.example.webservicestest.services.usecases;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.webservicestest.entities.User;
import com.example.webservicestest.services.WebServiceWrite;

import java.util.HashMap;
import java.util.Map;

public class InsertUserUC extends WebServiceWrite {

    public InsertUserUC(Context context, RequestAcceptedListener<Boolean> requestAcceptedListener, RequestRejectedListener requestRejectedListener) {
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
                Log.d("JSON", "Insert user request rejected: " + error);
                requestRejectedListener.onRequestRejected();
            }
        };
    }

}
