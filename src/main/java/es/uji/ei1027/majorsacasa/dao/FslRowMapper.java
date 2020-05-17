package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.FranjaServicioLimpieza;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class FslRowMapper implements RowMapper<FranjaServicioLimpieza> {

    public FranjaServicioLimpieza mapRow(ResultSet rs, int rowNum) throws SQLException {
        FranjaServicioLimpieza fsl = new FranjaServicioLimpieza();
        fsl.setId_franja(rs.getInt("id_franja"));
        fsl.setNick_empresa(rs.getString("nick_empresa"));
        fsl.setNick_demandante(rs.getString("nick_demandante"));
        fsl.setDiaSemana(rs.getString("dia_semana"));
        fsl.sethIni(rs.getObject("h_ini", LocalTime.class));
        fsl.sethFin(rs.getObject("h_fin", LocalTime.class));

        return fsl;
    }
}
