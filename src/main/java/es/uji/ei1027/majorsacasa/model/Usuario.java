package es.uji.ei1027.majorsacasa.model;

public class Usuario {

    String nick;
    String pass;
    ROL_USUARIO rol;

    public String getNick() {
        return this.nick;
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

    public String getRol() {
        return rol.toString();
    }

    public void setRol(ROL_USUARIO rol) {
        this.rol = rol;
    }

}
