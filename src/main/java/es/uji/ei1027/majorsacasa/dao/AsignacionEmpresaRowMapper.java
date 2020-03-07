package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.AsignacionEmpresa;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class AsignacionEmpresaRowMapper implements RowMapper<AsignacionEmpresa> {
    @Override
    public AsignacionEmpresa mapRow(ResultSet rs, int rowNum) throws SQLException {
        AsignacionEmpresa asignacionEmpresa = new AsignacionEmpresa();
        asignacionEmpresa.setId(rs.getInt("id"));
        asignacionEmpresa.setNick(rs.getString("nick"));
        asignacionEmpresa.setPrecio(rs.getFloat("precio"));
        return asignacionEmpresa;
    }
}
