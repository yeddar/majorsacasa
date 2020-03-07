package es.uji.ei1027.majorsacasa.model;

public class PreferenciaVoluntario {

    private String nick;
    private String preferencia;

    public PreferenciaVoluntario(){

    }

    // *********************************
    // ************ GETTERS ************
    // *********************************

    public String getNick() {
        return nick;
    }

    public String getPreferencia() {
        return preferencia;
    }

    // *********************************
    // ************ SETTERS ************
    // *********************************

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setPreferencia(String preferencia) {
        this.preferencia = preferencia;
    }

    @Override
    public String toString() {
        return "PreferenciaVoluntario{" +
                "nick='" + nick + '\'' +
                ", preferencia=" + preferencia +
                '}';
    }
}
