package es.uji.ei1027.majorsacasa.model;

public class RespEmpresa {
    String nick;
    String nombre;
    String cif;
    String tlf;
    String correo;

    public RespEmpresa() {
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

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
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

    @Override
    public String toString() {
        return "RespEmpresa{" +
                "nick='" + nick + '\'' +
                ", nombre='" + nombre + '\'' +
                ", cif='" + cif + '\'' +
                ", tlf='" + tlf + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
