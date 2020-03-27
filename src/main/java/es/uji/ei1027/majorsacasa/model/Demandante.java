package es.uji.ei1027.majorsacasa.model;

public class Demandante extends Usuario{

    String nick;
    String nombre;
    int edad;

    String tlf;
    String correo;
    String direccion;

    String cod_asist;
    boolean es_activo;

    public Demandante(){
        super();
    }

    @Override
    public String getNick() {
        return nick;
    }

    @Override
    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String getPass() {
        return super.getPass();
    }

    @Override
    public void setPass(String pass) {
        super.setPass(pass);
    }

    @Override
    public String getRol() {
        return super.getRol();
    }

    @Override
    public void setRol(ROL_USUARIO tipo) {
        super.setRol(tipo);
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
}
