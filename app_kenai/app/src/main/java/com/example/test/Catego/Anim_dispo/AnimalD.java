package com.example.test.Catego.Anim_dispo;

public class AnimalD {

    String nombre;
    String descrip;
    String imagen;

    public AnimalD() {
    }

    public AnimalD(String nombre, String imagen,String descript) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.descrip = descript;

    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
