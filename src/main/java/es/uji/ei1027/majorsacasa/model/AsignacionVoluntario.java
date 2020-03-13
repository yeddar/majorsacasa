package es.uji.ei1027.majorsacasa.model;

public class AsignacionVoluntario {

    private int id;
    private String nick;

    public AsignacionVoluntario() {

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

    // *********************************
    // ************ SETTERS ************
    // *********************************

    public void setId(int id) {
        this.id = id;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
