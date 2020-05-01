package es.uji.ei1027.majorsacasa.model;

import java.time.LocalTime;

public class ServicioCatering {

    private String nick_demandante;
    private String nick_empresa;
    private String tipo_comida;
    private String dias_semana;
    private LocalTime hora_aprox;

    public ServicioCatering() {
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

    public void setTipo_comida(String tipo_comida) {
        this.tipo_comida = tipo_comida;
    }

    public void setDias_semana(String dias_semana) {
        this.dias_semana = dias_semana;
    }

    public void setHora_aprox(LocalTime hora_aprox) {
        this.hora_aprox = hora_aprox;
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

    public String getTipo_comida() {
        return tipo_comida;
    }

    public String getDias_semana() {
        return dias_semana;
    }

    public LocalTime getHora_aprox() {
        return hora_aprox;
    }

    // *********************************
    // *********** TO STRING ***********
    // *********************************

    @Override
    public String toString() {
        return "ServicioCatering{" +
                "nick_demandante='" + nick_demandante + '\'' +
                ", nick_empresa='" + nick_empresa + '\'' +
                ", tipo_comida='" + tipo_comida + '\'' +
                ", dias_semana='" + dias_semana + '\'' +
                ", hora_aprox=" + hora_aprox +
                '}';
    }
}
