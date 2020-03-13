package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Demandante;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class DemandanteRowMapper implements RowMapper<Demandante> {

    public Demandante mapRow(ResultSet rs, int rowNum) throws SQLException {
        Demandante demandante = new Demandante();

        demandante.setNick(rs.getString("nick"));
        demandante.setNombre(rs.getString("nombre"));
        demandante.setEdad(rs.getInt("edad"));

        demandante.setTlf(rs.getString("tlf"));
        demandante.setCorreo(rs.getString("correo"));
        demandante.setDireccion(rs.getString("direccion"));

        demandante.setCod_asist(rs.getString("cod_asist"));
        demandante.setEs_activo(rs.getBoolean("es_activo"));


        return demandante;
    }
}
