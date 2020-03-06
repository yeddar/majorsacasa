package es.uji.ei1027.majorsacasa.model;

import java.time.LocalTime;

public class FranjaServicioEmpresa {

    int id;
    int servEmpresaId;
    String diaSemana;
    LocalTime hIni;
    LocalTime hFin;
    int vacantes;

    public FranjaServicioEmpresa(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServEmpresaId() {
        return servEmpresaId;
    }

    public void setServEmpresaId(int servEmpresaId) {
        this.servEmpresaId = servEmpresaId;
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

    public int getVacantes() {
        return vacantes;
    }

    public void setVacantes(int vacantes) {
        this.vacantes = vacantes;
    }

    @Override
    public String toString() {
        return "FranjaServicioEmpresa{" +
                "id=" + id +
                ", servEmpresaId=" + servEmpresaId +
                ", diaSemana='" + diaSemana + '\'' +
                ", hIni=" + hIni +
                ", hFin=" + hFin +
                ", vacantes=" + vacantes +
                '}';
    }
}
