package com.example.webservicestest.negocios.validadores;

import com.example.webservicestest.entidades.Usuario;

import java.util.regex.Pattern;

public class ValidadorUsuario extends Validador<Usuario> {

    public ValidadorUsuario(Usuario usuario) {
        super(usuario);
    }

    @Override
    protected void definirValidaciones() {

        // Id
        agregarValidacion(new ValidadorPropiedad() {
            @Override
            public boolean validar() {
                return t.getId() >= 0;
            }

        }, new ErrorValidacion() {
            @Override
            public String mensajeError() {
                return "El id debe de estar entre el rango 0 - 100";
            }

            @Override
            public Object propiedadInvalida() {
                return t.getId();
            }
        });

        // Nombre
        agregarValidacion(new ValidadorPropiedad() {
            @Override
            public boolean validar() {
                return !t.getNombre().isEmpty();
            }

        }, new ErrorValidacion() {
            @Override
            public String mensajeError() {
                return "El nombre no puede estar vacio";
            }

            @Override
            public Object propiedadInvalida() {
                return t.getNombre();
            }
        });

        agregarValidacion(new ValidadorPropiedad() {
            @Override
            public boolean validar() {
                return !Pattern.compile("[^a-zA-Z]")
                        .matcher(t.getNombre())
                        .find();
            }
        }, new ErrorValidacion() {

            @Override
            public String mensajeError() {
                return "El nombre solo puede contener letras de la 'A' a la 'Z'";
            }

            @Override
            public Object propiedadInvalida() {
                return t.getNombre();
            }
        });

        // Apellidos
        agregarValidacion(new ValidadorPropiedad() {
            @Override
            public boolean validar() {
                return !t.getApellido().isEmpty();
            }

        }, new ErrorValidacion() {
            @Override
            public String mensajeError() {
                return "El apellido no puede estar vacio";
            }

            @Override
            public Object propiedadInvalida() {
                return t.getApellido();
            }
        });

        // Fecha nacimiento
        agregarValidacion(new ValidadorPropiedad() {
            @Override
            public boolean validar() {
                try {
                    String[] fechaNacimiento = t.getFechaNacimiento().split("-");

                    if (fechaNacimiento.length < 3)
                        return false;

                    Integer.parseInt(fechaNacimiento[0]);
                    Integer.parseInt(fechaNacimiento[1]);
                    Integer.parseInt(fechaNacimiento[2]);

                    return true;
                } catch (Exception ex) {
                    return false;
                }
            }

        }, new ErrorValidacion() {
            @Override
            public String mensajeError() {
                return "La fecha debe de tener el siguiente formato: 'AAAA-MM-DD'";
            }

            @Override
            public Object propiedadInvalida() {
                return t.getFechaNacimiento();
            }
        });
    }
}
