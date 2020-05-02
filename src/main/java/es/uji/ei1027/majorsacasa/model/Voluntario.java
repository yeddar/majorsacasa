package es.uji.ei1027.majorsacasa.model;

public class Voluntario extends Usuario {

    private String nick;
    private String nombre;
    private int edad;
    private String tlf;
    private String email;
    private String aficiones;
    private String status;

    public Voluntario(){
        super();
    }

    // *********************************
    // ************ GETTERS ************
    // *********************************

    @Override
    public String getNick() {
        return nick;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getTlf() {
        return tlf;
    }

    public String getEmail() {
        return email;
    }

    public String getAficiones() {
        return aficiones;
    }

    public String getStatus() {
        return status;
    }

    // *********************************
    // ************ SETTERS ************
    // *********************************

    @Override
    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAficiones(String aficiones) {
        this.aficiones = aficiones;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // *********************************
    // *********** TO STRING ***********
    // *********************************

    @Override
    public String toString() {
        return "Voluntario{" +
                "nick='" + nick + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", tlf='" + tlf + '\'' +
                ", email='" + email + '\'' +
                ", aficiones='" + aficiones + '\'' +
                ", status='" + status + '\'' +
                ", nick='" + nick + '\'' +
                ", pass='" + pass + '\'' +
                ", rol=" + rol +
                '}';
    }
}
