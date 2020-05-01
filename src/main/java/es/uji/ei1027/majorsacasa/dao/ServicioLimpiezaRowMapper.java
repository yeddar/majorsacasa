package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.ServicioLimpieza;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ServicioLimpiezaRowMapper implements RowMapper<ServicioLimpieza> {
    @Override
    public ServicioLimpieza mapRow(ResultSet resultSet, int i) throws SQLException {
        ServicioLimpieza servicioLimpieza = new ServicioLimpieza();
        servicioLimpieza.setNick_demandante(resultSet.getString("nick_demandante"));
        servicioLimpieza.setNick_empresa(resultSet.getString("nick_empresa"));
        servicioLimpieza.setHoras(resultSet.getInt("cant_horas"));
        return servicioLimpieza;
    }
}