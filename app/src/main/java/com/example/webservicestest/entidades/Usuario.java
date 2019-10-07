package com.example.webservicestest.entidades;

public class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String rutaImagen;

    private Usuario(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.fechaNacimiento = builder.fechaNacimiento;
        this.rutaImagen = builder.rutaImagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public static class Builder {

        private int id;
        private String nombre;
        private String apellido;
        private String fechaNacimiento;
        private String rutaImagen;

        public Builder() {}

        public Usuario build() {
            return new Usuario(this);
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder setApellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Builder setFechaNacimiento(String fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
            return this;
        }

        public Builder setRutaImagen(String rutaImagen) {
            this.rutaImagen = rutaImagen;
            return this;
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", rutaImagen='" + rutaImagen + '\'' +
                '}';
    }
}
