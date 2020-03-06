package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.ServEmpresa;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServEmpresaRowMapper implements RowMapper<ServEmpresa> {

    public ServEmpresa mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        ServEmpresa servEmpresa = new ServEmpresa();
        servEmpresa.setId(rs.getInt("id"));
        servEmpresa.setRespEmpresaNick(rs.getString("resp_empresa_nick"));
        servEmpresa.setTipoServEmp(rs.getString("tipo_serv_emp"));

        return servEmpresa;
    }
}
