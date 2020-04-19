package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Empresa;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServEmpresaRowMapper implements RowMapper<Empresa> {
    @Override
    public Empresa mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    /*
    public Empresa mapRow(ResultSet rs, int rowNum) throws SQLException {
        Empresa empresa = new Empresa();
        empresa.setId(rs.getInt("id"));
        empresa.setRespEmpresaNick(rs.getString("resp_empresa_nick"));
        empresa.setTipoServEmp(rs.getString("tipo_serv_emp"));

        return empresa;
    }

     */
}
