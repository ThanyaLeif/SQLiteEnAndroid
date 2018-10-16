package net.ivanvega.sqliteenandroid.db;

public class Usuario {

    int id;
    String nombre;
    String telefono;
    String email;
    String red_social;
    String fecha_nac;

    public Usuario () { }

    public Usuario(int id, String nombre, String telefono, String email, String red_social, String fecha_nac) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.red_social = red_social;
        this.fecha_nac = fecha_nac;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRed_social(String red_social) {
        this.red_social = red_social;
    }

    public void setFecha_nac(String fecha_nac){
        this.fecha_nac = fecha_nac;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getRed_social() {
        return red_social;
    }

    public String getFecha_nac() {
        return fecha_nac;
    }
}
