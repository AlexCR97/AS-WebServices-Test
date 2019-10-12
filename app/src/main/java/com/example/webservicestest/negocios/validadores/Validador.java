package com.example.webservicestest.negocios.validadores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Validador<T> {

    public interface ValidadorPropiedad {
        boolean validar();
    }

    public interface ErrorValidacion {
        String mensajeError();
        Object propiedadInvalida();
    }

    protected T t;
    private List<ValidadorPropiedad> validaciones = new ArrayList<>();
    private Map<ValidadorPropiedad, ErrorValidacion> errores = new HashMap<>();
    private ErrorValidacion ultimoError;

    protected Validador(T t) {
        this.t = t;
        definirValidaciones();
    }

    protected abstract void definirValidaciones();

    protected void agregarValidacion(ValidadorPropiedad validadorPropiedad) {
        validaciones.add(validadorPropiedad);
    }

    protected void agregarValidacion(ValidadorPropiedad validadorPropiedad, ErrorValidacion errorValidacion) {
        validaciones.add(validadorPropiedad);

        if (errorValidacion != null)
            errores.put(validadorPropiedad, errorValidacion);
    }

    public ErrorValidacion ultimoError() {
        return ultimoError;
    }

    public boolean validar() {
        for (ValidadorPropiedad validadorPropiedad : validaciones) {
            if (!validadorPropiedad.validar()) {

                ErrorValidacion errorValidacion = errores.get(validadorPropiedad);
                if (errorValidacion != null)
                    ultimoError = errorValidacion;

                return false;
            }
        }
        return true;
    }
}
