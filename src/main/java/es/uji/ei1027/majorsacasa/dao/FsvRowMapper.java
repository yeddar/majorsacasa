package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.FranjaServicioVoluntario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public final class FsvRowMapper implements RowMapper<FranjaServicioVoluntario> {

    public FranjaServicioVoluntario mapRow(ResultSet rs, int rowNum) throws SQLException {
        FranjaServicioVoluntario franjaServicioVoluntario = new FranjaServicioVoluntario();

        franjaServicioVoluntario.setId(rs.getInt("id"));
        franjaServicioVoluntario.setNick(rs.getString("nick"));
        franjaServicioVoluntario.setDiaSemana(rs.getString("dia_semana"));
        Time t1 = rs.getTime("h_ini");
        franjaServicioVoluntario.sethIni(t1 != null ? t1.toLocalTime() : null);
        Time t2 = rs.getTime("h_fin");
        franjaServicioVoluntario.sethIni(t2 != null ? t2.toLocalTime() : null);

        return franjaServicioVoluntario;
    }
}
