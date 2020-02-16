package es.uji.ei1027.majorsacasa.model;

public class Demandante {

    String nick;
    String pass;
    String nombre;
    int edad;
    String tlf;
    String cod_asist;
    String correo;

    public Demandante(){
        super();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getCod_asist() {
        return cod_asist;
    }

    public void setCod_asist(String cod_asist) {
        this.cod_asist = cod_asist;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
