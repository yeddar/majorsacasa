package es.uji.ei1027.majorsacasa.model;

public class Demandante extends Usuario{

    String nombre;
    int edad;

    String tlf;
    String correo;
    String direccion;

    String cod_asist;
    String status;

    public Demandante(){
        super();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
