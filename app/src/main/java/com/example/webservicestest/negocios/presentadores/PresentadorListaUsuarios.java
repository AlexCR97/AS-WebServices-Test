package com.example.webservicestest.negocios.presentadores;

import com.example.webservicestest.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PresentadorListaUsuarios extends Presentador<List<Usuario>> {

    @Override
    public List<Usuario> procesar(JSONObject json) {
        JSONArray jsonArray = json.optJSONArray("user");
        List<Usuario> usuarios = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            assert jsonObject != null;
            Usuario user = new Usuario.Builder()
                    .setId(jsonObject.optInt("id"))
                    .setNombre(jsonObject.optString("name"))
                    .setApellido(jsonObject.optString("last_name"))
                    .setFechaNacimiento(jsonObject.optString("birth_date"))
                    .setRutaImagen(jsonObject.optString("image"))
                    .build();

            usuarios.add(user);
        }
        return usuarios;
    }
}
