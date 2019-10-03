package com.example.webservicestest.services.usecases;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.webservicestest.entities.User;
import com.example.webservicestest.services.WebServiceRead;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListUsersUC extends WebServiceRead<List<User>> {

    public ListUsersUC(Context context, RequestAcceptedListener<List<User>> requestAcceptedListener, RequestRejectedListener requestRejectedListener) {
        super(context, requestAcceptedListener, requestRejectedListener);
    }

    @Override
    protected String defineUrl(Object... params) {
        return "http://192.168.0.2/web_services_test/WSSelectUsers.php";
    }

    @Override
    protected Response.Listener<JSONObject> defineResponseListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("JSON", "Response: " + response.toString());

                try {
                    JSONArray jsonArray = response.optJSONArray("user");
                    List<User> users = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        User user = new User.Builder()
                                .setId(jsonObject.optInt("id"))
                                .setName(jsonObject.optString("name"))
                                .setLastName(jsonObject.optString("last_name"))
                                .setBirthDate(jsonObject.optString("birth_date"))
                                .setImage(jsonObject.optString("image"))
                                .build();

                        users.add(user);
                    }

                    requestAcceptedListener.onRequestAccepted(users);
                }
                catch (JSONException ex) {
                    Log.d("JSON", "JSONException: " + ex.getMessage());
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
                Log.d("JSON", "Error response: " + error.getMessage());
                requestRejectedListener.onRequestRejected();
            }
        };
    }
}
