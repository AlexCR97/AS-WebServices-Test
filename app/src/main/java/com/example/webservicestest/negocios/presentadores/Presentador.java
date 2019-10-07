package com.example.webservicestest.negocios.presentadores;

import org.json.JSONObject;

public abstract class Presentador<T> {

    public abstract T procesar(JSONObject json);

}
