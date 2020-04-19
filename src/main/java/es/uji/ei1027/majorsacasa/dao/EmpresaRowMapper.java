package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Empresa;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpresaRowMapper implements RowMapper<Empresa> {

    public Empresa mapRow(ResultSet rs, int rowNum) throws SQLException {
        Empresa empresa = new Empresa();

        empresa.setNick(rs.getString("nick"));
        empresa.setNombre(rs.getString("nombre"));
        empresa.setNif(rs.getString("nif"));

        empresa.setTlf(rs.getString("tlf"));
        empresa.setEmail(rs.getString("email"));
        empresa.setTipo_empresa(rs.getString("tipo_empresa"));

        empresa.setVacantes(rs.getInt("vacantes"));

        return empresa;
    }
}
