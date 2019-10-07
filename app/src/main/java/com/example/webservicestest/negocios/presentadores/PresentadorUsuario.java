package com.example.webservicestest.negocios.presentadores;

import com.example.webservicestest.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PresentadorUsuario {

    public Usuario procesar(JSONObject json) {
        JSONArray jsonArray = json.optJSONArray("user");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assert jsonObject != null;
        String name = jsonObject.optString("name");
        String lastName = jsonObject.optString("last_name");
        String birthDate = jsonObject.optString("birth_date");
        String image = jsonObject.optString("image");

        return new Usuario.Builder()
                .setNombre(name)
                .setApellido(lastName)
                .setFechaNacimiento(birthDate)
                .setRutaImagen(image)
                .build();
    }

}
