package es.uji.ei1027.majorsacasa.model;

import java.time.LocalDate;

public class AsignacionVoluntario {

    private int id_franja;
    private String nick_demandante;
    private LocalDate f_ini;
    private LocalDate f_fin;
    private String serv_status;
    private String feedback_voluntario;

    public AsignacionVoluntario() {

    }

    // *********************************
    // ************ GETTERS ************
    // *********************************

    public int getId_franja() {
        return id_franja;
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

    public String getFeedback_voluntario() {
        return feedback_voluntario;
    }

    // *********************************
    // ************ SETTERS ************
    // *********************************

    public void setId_franja(int id_franja) {
        this.id_franja = id_franja;
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

    public void setFeedback_voluntario(String feedback_voluntario) {
        this.feedback_voluntario = feedback_voluntario;
    }

    // *********************************
    // *********** TO STRING ***********
    // *********************************

    @Override
    public String toString() {
        return "AsignacionVoluntario{" +
                "id_franja=" + id_franja +
                ", nick_demandante='" + nick_demandante + '\'' +
                ", f_ini=" + f_ini +
                ", f_fin=" + f_fin +
                ", serv_status='" + serv_status + '\'' +
                ", feedback_voluntario='" + feedback_voluntario + '\'' +
                '}';
    }
}
