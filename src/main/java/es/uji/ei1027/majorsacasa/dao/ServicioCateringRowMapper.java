package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.ServicioCatering;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public final class ServicioCateringRowMapper implements RowMapper<ServicioCatering> {
    @Override
    public ServicioCatering mapRow(ResultSet resultSet, int i) throws SQLException {
        ServicioCatering servicioCatering = new ServicioCatering();
        servicioCatering.setNick_demandante(resultSet.getString("nick_demandante"));
        servicioCatering.setNick_empresa(resultSet.getString("nick_empresa"));
        servicioCatering.setTipo_comida(resultSet.getString("tipo_comida"));
        servicioCatering.setHora_aprox(resultSet.getObject("hora_aprox", LocalTime.class));
        return servicioCatering;
    }
}
