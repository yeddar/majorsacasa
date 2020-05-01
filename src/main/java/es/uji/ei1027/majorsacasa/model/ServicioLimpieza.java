package es.uji.ei1027.majorsacasa.model;

public class ServicioLimpieza {

    private String nick_demandante;
    private String nick_empresa;
    private int horas;

    public ServicioLimpieza(){
        super();
    }

    // *********************************
    // ************ SETTERS ************
    // *********************************

    public void setNick_demandante(String nick_demandante) {
        this.nick_demandante = nick_demandante;
    }

    public void setNick_empresa(String nick_empresa) {
        this.nick_empresa = nick_empresa;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    // *********************************
    // ************ GETTERS ************
    // *********************************

    public String getNick_demandante() {
        return nick_demandante;
    }

    public String getNick_empresa() {
        return nick_empresa;
    }

    public int getHoras() {
        return horas;
    }

    // *********************************
    // *********** TO STRING ***********
    // *********************************

    @Override
    public String toString() {
        return "ServicioLimpieza{" +
                "nick_demandante='" + nick_demandante + '\'' +
                ", nick_empresa='" + nick_empresa + '\'' +
                ", horas=" + horas +
                '}';
    }
}
