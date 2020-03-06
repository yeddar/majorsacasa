package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.RespEmpresa;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RespEmpresaRowMapper implements RowMapper<RespEmpresa> {

    public RespEmpresa mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        RespEmpresa respEmpresa = new RespEmpresa();
        respEmpresa.setNick(rs.getString("nick"));
        respEmpresa.setNombre(rs.getString("nombre"));
        respEmpresa.setCif(rs.getString("cif"));
        respEmpresa.setTlf(rs.getString("tlf"));
        respEmpresa.setCorreo(rs.getString("correo"));

        return respEmpresa;
    }
}
