package es.uji.ei1027.majorsacasa.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public class FranjaServicioVoluntario {
    private int id;
    private String nick;
    private String diaSemana;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hIni;
    private LocalTime hFin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime gethIni() {
        return hIni;
    }

    public void sethIni(LocalTime hIni) {
        this.hIni = hIni;
    }

    public LocalTime gethFin() {
        return hFin;
    }

    public void sethFin(LocalTime hFin) {
        this.hFin = hFin;
    }

    @Override
    public String toString() {
        return "FranjaServicioVoluntario{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", diaSemana='" + diaSemana + '\'' +
                ", hIni=" + hIni +
                ", hFin=" + hFin +
                '}';
    }
}
