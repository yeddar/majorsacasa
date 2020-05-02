package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.AsignacionVoluntario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class AsignacionVoluntarioRowMapper implements RowMapper<AsignacionVoluntario> {

    public AsignacionVoluntario mapRow(ResultSet rs, int rowNum) throws SQLException {
        AsignacionVoluntario asignacionVoluntario = new AsignacionVoluntario();
        asignacionVoluntario.setId_franja(rs.getInt("id_franja"));
        asignacionVoluntario.setNick_demandante(rs.getString("nick_demandante"));
        asignacionVoluntario.setF_ini(rs.getObject("f_ini", LocalDate.class));
        asignacionVoluntario.setF_fin(rs.getObject("f_fin", LocalDate.class));
        asignacionVoluntario.setServ_status(rs.getString("serv_status"));
        asignacionVoluntario.setFeedback_voluntario(rs.getString("feedback_voluntario"));
        return asignacionVoluntario;
    }

}
