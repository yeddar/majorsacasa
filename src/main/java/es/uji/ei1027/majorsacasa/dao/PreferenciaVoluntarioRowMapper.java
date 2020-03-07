package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.PreferenciaVoluntario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PreferenciaVoluntarioRowMapper implements RowMapper<PreferenciaVoluntario> {

    public PreferenciaVoluntario mapRow(ResultSet rs, int rowNum) throws SQLException {
        PreferenciaVoluntario preferenciaVoluntario = new PreferenciaVoluntario();
        preferenciaVoluntario.setNick(rs.getString("nick"));
        preferenciaVoluntario.setPreferencia(rs.getString("habilidad"));
        return preferenciaVoluntario;
    }
}
