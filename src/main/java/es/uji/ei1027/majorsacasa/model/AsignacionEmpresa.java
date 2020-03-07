package es.uji.ei1027.majorsacasa.model;

public class AsignacionEmpresa {
    private int id;
    private String nick;
    private float precio;

    public AsignacionEmpresa(){

    }

    // *********************************
    // ************ GETTERS ************
    // *********************************

    public int getId() {
        return id;
    }

    public String getNick() {
        return nick;
    }

    public float getPrecio() {
        return precio;
    }

    // *********************************
    // ************ SETTERS ************
    // *********************************

    public void setId(int id) {
        this.id = id;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
