package es.uji.ei1027.majorsacasa.model;

import java.time.LocalDate;

public class ServicioEmpresa {

    private String nick_empresa;
    private String nick_demandante;
    private LocalDate f_ini;
    private LocalDate f_fin;
    private String serv_status;

    public ServicioEmpresa() {
        super();
    }

    // *********************************
    // ************ GETTERS ************
    // *********************************

    public String getNick_empresa() {
        return nick_empresa;
    }

    public String getNick_demandante() {
        return nick_demandante;
    }

    public LocalDate getF_ini() {
        return f_ini;
    }

    public LocalDate getF_fin() {
        return f_fin;
    }

    public String getServ_status() {
        return serv_status;
    }


    // *********************************
    // ************ SETTERS ************
    // *********************************

    public void setNick_empresa(String nick_empresa) {
        this.nick_empresa = nick_empresa;
    }

    public void setNick_demandante(String nick_demandante) {
        this.nick_demandante = nick_demandante;
    }

    public void setF_ini(LocalDate f_ini) {
        this.f_ini = f_ini;
    }

    public void setF_fin(LocalDate f_fin) {
        this.f_fin = f_fin;
    }

    public void setServ_status(String serv_status) {
        this.serv_status = serv_status;
    }

    // *********************************
    // *********** TO STRING ***********
    // *********************************


    @Override
    public String toString() {
        return "ServicioEmpresa{" +
                "nick_empresa='" + nick_empresa + '\'' +
                ", nick_demandante='" + nick_demandante + '\'' +
                ", f_ini=" + f_ini +
                ", f_fin=" + f_fin +
                ", serv_status='" + serv_status + '\'' +
                '}';
    }
}
