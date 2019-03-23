package com.jonathanvillafuerte.contactosuteq.Adapters;

public class dataContacts {
    public String Nombres;
    public String Apellidos;
    public String Url_Foto;
    public String Telefono_Hogar;
    public String Telefono_Personal;
    public String Telefono_Laboral;
    public String correo_electronico;
    public String Latitud;
    public String Longitud;


    public dataContacts(String nombres, String apellidos, String url_Foto, String telefono_Hogar, String telefono_Personal, String telefono_Laboral, String correo_electronico, String latitud, String longitud) {
        Nombres = nombres;
        Apellidos = apellidos;
        Url_Foto = url_Foto;
        Telefono_Hogar = telefono_Hogar;
        Telefono_Personal = telefono_Personal;
        Telefono_Laboral = telefono_Laboral;
        this.correo_electronico = correo_electronico;
        Latitud = latitud;
        Longitud = longitud;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getUrl_Foto() {
        return Url_Foto;
    }

    public void setUrl_Foto(String url_Foto) {
        Url_Foto = url_Foto;
    }

    public String getTelefono_Hogar() {
        return Telefono_Hogar;
    }

    public void setTelefono_Hogar(String telefono_Hogar) {
        Telefono_Hogar = telefono_Hogar;
    }

    public String getTelefono_Personal() {
        return Telefono_Personal;
    }

    public void setTelefono_Personal(String telefono_Personal) {
        Telefono_Personal = telefono_Personal;
    }

    public String getTelefono_Laboral() {
        return Telefono_Laboral;
    }

    public void setTelefono_Laboral(String telefono_Laboral) {
        Telefono_Laboral = telefono_Laboral;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }
}
