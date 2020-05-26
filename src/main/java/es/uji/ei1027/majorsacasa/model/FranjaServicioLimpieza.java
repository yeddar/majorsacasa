package es.uji.ei1027.majorsacasa.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public class FranjaServicioLimpieza {

    int id_franja;
    String nick_empresa;
    String nick_demandante;
    String diaSemana;
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime hIni;
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime hFin;

    public FranjaServicioLimpieza() {
        super();
    }

    // *********************************
    // ************ SETTERS ************
    // *********************************

    public void setId_franja(int id_franja) {
        this.id_franja = id_franja;
    }

    public void setNick_empresa(String nick_empresa) {
        this.nick_empresa = nick_empresa;
    }

    public void setNick_demandante(String nick_demandante) {
        this.nick_demandante = nick_demandante;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void sethIni(LocalTime hIni) {
        this.hIni = hIni;
    }

    public void sethFin(LocalTime hFin) {
        this.hFin = hFin;
    }

    // *********************************
    // ************ GETTERS ************
    // *********************************

    public int getId_franja() {
        return id_franja;
    }

    public String getNick_empresa() {
        return nick_empresa;
    }

    public String getNick_demandante() {
        return nick_demandante;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public LocalTime gethIni() {
        return hIni;
    }

    public LocalTime gethFin() {
        return hFin;
    }

    // *********************************
    // *********** TO STRING ***********
    // *********************************

    @Override
    public String toString() {
        return "FranjaServicioLimpieza{" +
                "id_franja=" + id_franja +
                ", nick_empresa='" + nick_empresa + '\'' +
                ", nick_demandante='" + nick_demandante + '\'' +
                ", diaSemana=" + diaSemana +
                ", hIni=" + hIni +
                ", hFin=" + hFin +
                '}';
    }
}
