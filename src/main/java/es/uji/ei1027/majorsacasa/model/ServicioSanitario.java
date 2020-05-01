package es.uji.ei1027.majorsacasa.model;

import java.time.LocalDate;

public class ServicioSanitario {

    private String nick_demandante;
    private String nick_empresa;
    private String necesidad;
    private LocalDate dia_visita;
    private String manana_tarde;

    public ServicioSanitario(){
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

    public void setNecesidad(String necesidad) {
        this.necesidad = necesidad;
    }

    public void setDia_visita(LocalDate dia_visita) {
        this.dia_visita = dia_visita;
    }

    public void setManana_tarde(String manana_tarde) {
        this.manana_tarde = manana_tarde;
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

    public String getNecesidad() {
        return necesidad;
    }

    public LocalDate getDia_visita() {
        return dia_visita;
    }

    public String getManana_tarde() {
        return manana_tarde;
    }

    // *********************************
    // *********** TO STRING ***********
    // *********************************

    @Override
    public String toString() {
        return "ServicioSanitario{" +
                "nick_demandante='" + nick_demandante + '\'' +
                ", nick_empresa='" + nick_empresa + '\'' +
                ", necesidad='" + necesidad + '\'' +
                ", dia_visita=" + dia_visita +
                ", manana_tarde='" + manana_tarde + '\'' +
                '}';
    }
}
