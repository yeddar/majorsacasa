package es.uji.ei1027.majorsacasa.model;

public class Voluntario extends Usuario {
    private String nombre;
    private int edad;

    private String tlf;
    private String correo;
    private boolean esActivo;


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

    public boolean isEsActivo() {
        return esActivo;
    }

    public void setEsActivo(boolean esActivo) {
        this.esActivo = esActivo;
    }



    @Override
    public String toString() {
        return "Voluntario{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", tlf='" + tlf + '\'' +
                ", correo='" + correo + '\'' +
                ", esActivo=" + esActivo +
                ", nick='" + nick + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

}
