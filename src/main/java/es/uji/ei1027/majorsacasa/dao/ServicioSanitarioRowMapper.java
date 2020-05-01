package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.ServicioSanitario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ServicioSanitarioRowMapper implements RowMapper<ServicioSanitario> {
    @Override
    public ServicioSanitario mapRow(ResultSet resultSet, int i) throws SQLException {
        ServicioSanitario servicioSanitario = new ServicioSanitario();
        servicioSanitario.setNick_demandante(resultSet.getString("nick_demandante"));
        servicioSanitario.setNick_empresa(resultSet.getString("nick_empresa"));
        servicioSanitario.setNecesidad(resultSet.getString("necesidad"));
        servicioSanitario.setDia_visita(resultSet.getObject("dia_visita", LocalDate.class));
        servicioSanitario.setManana_tarde(resultSet.getString("manana_tarde"));
        return servicioSanitario;
    }
}