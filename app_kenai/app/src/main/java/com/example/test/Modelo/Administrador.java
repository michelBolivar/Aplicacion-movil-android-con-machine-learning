package com.example.test.Modelo;

public class Administrador {

    String UID, NOMBRES, ROL, CORREO, IMAGEN;

    public Administrador() {
    }

    public Administrador(String UID, String NOMBRES, String ROL, String CORREO, String IMAGEN) {
        this.UID = UID;
        this.NOMBRES = NOMBRES;
        this.ROL = ROL;
        this.CORREO = CORREO;
        this.IMAGEN = IMAGEN;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getNOMBRES() {
        return NOMBRES;
    }

    public void setNOMBRES(String NOMBRES) {
        this.NOMBRES = NOMBRES;
    }

    public String getROL() {
        return ROL;
    }

    public void setROL(String ROL) {
        this.ROL = ROL;
    }

    public String getCORREO() {
        return CORREO;
    }

    public void setCORREO(String CORREO) {
        this.CORREO = CORREO;
    }

    public String getIMAGEN() {
        return IMAGEN;
    }

    public void setIMAGEN(String IMAGEN) {
        this.IMAGEN = IMAGEN;
    }
}
