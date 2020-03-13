package es.uji.ei1027.majorsacasa.model;

public class ServEmpresa {

    int id;
    String respEmpresaNick;
    String tipoServEmp;

    public ServEmpresa() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRespEmpresaNick() {
        return respEmpresaNick;
    }

    public void setRespEmpresaNick(String respEmpresaNick) {
        this.respEmpresaNick = respEmpresaNick;
    }

    public String getTipoServEmp() {
        return tipoServEmp;
    }

    public void setTipoServEmp(String tipoServEmp) {
        this.tipoServEmp = tipoServEmp;
    }

    @Override
    public String toString() {
        return "ServEmpresa{" +
                "id=" + id +
                ", respEmpresaNick='" + respEmpresaNick + '\'' +
                ", tipoServEmp='" + tipoServEmp + '\'' +
                '}';
    }
}
