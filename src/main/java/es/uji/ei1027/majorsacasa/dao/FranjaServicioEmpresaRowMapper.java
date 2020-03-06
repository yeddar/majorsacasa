package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.FranjaServicioEmpresa;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class FranjaServicioEmpresaRowMapper implements RowMapper<FranjaServicioEmpresa> {

    public FranjaServicioEmpresa mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        FranjaServicioEmpresa franjaServicioEmpresa = new FranjaServicioEmpresa();
        franjaServicioEmpresa.setId(rs.getInt("id"));
        franjaServicioEmpresa.setServEmpresaId(rs.getInt("serv_empresa_id"));
        franjaServicioEmpresa.setDiaSemana(rs.getString("dia_semana"));
        franjaServicioEmpresa.sethIni(rs.getObject("h_ini", LocalTime.class));
        franjaServicioEmpresa.sethFin(rs.getObject("h_fin", LocalTime.class));
        franjaServicioEmpresa.setVacantes(rs.getInt("vacantes"));

        return franjaServicioEmpresa;
    }
}
