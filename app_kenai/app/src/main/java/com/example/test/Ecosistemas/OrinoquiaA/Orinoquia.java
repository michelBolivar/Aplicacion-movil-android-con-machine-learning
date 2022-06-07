package com.example.test.Ecosistemas.OrinoquiaA;

public class Orinoquia {

    private String imagen;
    private String imagen2;
    private String nombre;
    private String descripcion1;
    private String descripcion2;
    private String descripcion3;

    public Orinoquia() {

    }

    public Orinoquia(String imagen, String imagen2, String nombre, String descripcion1, String descripcion2, String descripcion3) {
        this.imagen = imagen;
        this.imagen2 = imagen2;
        this.nombre = nombre;
        this.descripcion1 = descripcion1;
        this.descripcion2 = descripcion2;
        this.descripcion3 = descripcion3;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagen2() {
        return imagen2;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion1() {
        return descripcion1;
    }

    public void setDescripcion1(String descripcion1) {
        this.descripcion1 = descripcion1;
    }

    public String getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion2(String descripcion2) {
        this.descripcion2 = descripcion2;
    }

    public String getDescripcion3() {
        return descripcion3;
    }

    public void setDescripcion3(String descripcion3) {
        this.descripcion3 = descripcion3;
    }
}
