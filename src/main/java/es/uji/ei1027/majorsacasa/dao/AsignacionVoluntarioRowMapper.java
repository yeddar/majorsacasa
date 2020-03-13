package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.AsignacionVoluntario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class AsignacionVoluntarioRowMapper implements RowMapper<AsignacionVoluntario> {

    public AsignacionVoluntario mapRow(ResultSet rs, int rowNum) throws SQLException {
        AsignacionVoluntario asignacionVoluntario = new AsignacionVoluntario();
        asignacionVoluntario.setId(rs.getInt("id"));
        asignacionVoluntario.setNick(rs.getString("nick"));
        return asignacionVoluntario;
    }

}
