package com.example.webservicestest.servicios.implementaciones;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.webservicestest.entidades.Usuario;
import com.example.webservicestest.servicios.ServicioWebEscritura;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserUC extends ServicioWebEscritura {

    public UpdateUserUC(Context context, EventoPeticionAceptada<Boolean> requestAcceptedListener, EventoPeticionRechazada requestRejectedListener) {
        super(context, requestAcceptedListener, requestRejectedListener);
    }

    @Override
    protected Map<String, String> definirParams(Object... params) {
        int id = Integer.parseInt(params[0].toString());
        Usuario user = (Usuario) params[1];

        Map<String, String> args = new HashMap<>();
        args.put("id", String.valueOf(id));
        args.put("new_id", String.valueOf(user.getId()));
        args.put("name", user.getNombre());
        args.put("last_name", user.getApellido());
        args.put("birth_date", user.getFechaNacimiento());
        args.put("image", "dummy");

        return args;
    }

    @Override
    protected String definirUrl(Object... args) {
        return "http://192.168.0.2/web_services_test/WSUpdateUser.php";
    }

    @Override
    protected Response.Listener definirResponseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("JSON", "Insert user request accepted: " + response);

                if (response.trim().equalsIgnoreCase("1"))
                    eventoPeticionAceptada.alAceptarPeticion(true);
                else
                    eventoPeticionAceptada.alAceptarPeticion(false);
            }
        };
    }

    @Override
    protected Response.ErrorListener definirErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON", "Error response: " + error.getMessage());
                eventoPeticionRechazada.alRechazarPeticion();
            }
        };
    }
}
