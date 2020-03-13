package es.uji.ei1027.majorsacasa.model;

public class Demandante {

    String nick;
    String nombre;
    int edad;

    String tlf;
    String correo;
    String direccion;

    String cod_asist;
    boolean es_activo;

    public Demandante() {
        super();
    }


    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCod_asist() {
        return cod_asist;
    }

    public void setCod_asist(String cod_asist) {
        this.cod_asist = cod_asist;
    }

    public boolean isEs_activo() {
        return es_activo;
    }

    public void setEs_activo(boolean es_activo) {
        this.es_activo = es_activo;
    }

    @Override
    public String toString() {
        return "Demandante{" +
                "nick='" + nick + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", tlf='" + tlf + '\'' +
                ", correo='" + correo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", cod_asist='" + cod_asist + '\'' +
                ", es_activo=" + es_activo +
                '}';
    }
}
