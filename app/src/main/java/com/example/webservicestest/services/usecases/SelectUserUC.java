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

public class SelectUserUC extends WebServiceRead<User> {

    public SelectUserUC(Context context, RequestAcceptedListener<User> requestAcceptedListener, RequestRejectedListener requestRejectedListener) {
        super(context, requestAcceptedListener, requestRejectedListener);
    }

    @Override
    protected String defineUrl(Object... params) {
        int id = Integer.parseInt(params[0].toString());
        return "http://192.168.0.2/web_services_test/WSSelectUserId.php?id=" + id;
    }

    @Override
    protected Response.Listener<JSONObject> defineResponseListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("JSON", "Response: " + response.toString());

                    JSONArray jsonArray = response.optJSONArray("user");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    String name = jsonObject.optString("name");
                    String lastName = jsonObject.optString("last_name");
                    String birthDate = jsonObject.optString("birth_date");
                    String image = jsonObject.optString("image");

                    User user = new User.Builder()
                            .setName(name)
                            .setLastName(lastName)
                            .setBirthDate(birthDate)
                            .setImage(image)
                            .build();

                    Log.d("JSON", "Just created onResponse data: " + user.toString());

                    requestAcceptedListener.onRequestAccepted(user);

                } catch (JSONException e) {
                    Log.d("JSON", "JSONException: " + e.toString());
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
