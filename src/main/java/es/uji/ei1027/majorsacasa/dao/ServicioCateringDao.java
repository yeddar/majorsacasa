package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.ServicioCatering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServicioCateringDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    void addServicioCatering(ServicioCatering servicioCatering){
        jdbcTemplate.update(
                "INSERT INTO servicio_catering VALUES(?, ?, ?, ?, ?)", servicioCatering.getNick_empresa(),
                servicioCatering.getNick_demandante(), servicioCatering.getTipo_comida(), servicioCatering.getDias_semana(),
                servicioCatering.getHora_aprox()
        );
    }

    void deleteServicioCatering(ServicioCatering servicioCatering) {
        jdbcTemplate.update("DELETE FROM servicio_catering WHERE nick_empresa=? AND nick_demandante=?",
                servicioCatering.getNick_empresa(), servicioCatering.getNick_demandante());
    }

    void updateServicioCatering(ServicioCatering servicioCatering) {
        jdbcTemplate.update("UPDATE servicio_catering SET tipo_comida=?, dias_semana=?, hora_aprox=?" +
                        " WHERE nick_empresa=? AND nick_demandante=?",
                servicioCatering.getTipo_comida(), servicioCatering.getDias_semana(), servicioCatering.getHora_aprox(),
                servicioCatering.getNick_empresa(), servicioCatering.getNick_demandante()
        );
    }

    List<ServicioCatering> getServiciosCatering() {
        try {
            return jdbcTemplate.query("SELECT * FROM servicio_catering",
                    new ServicioCateringRowMapper());
        } catch (Exception e) {
            return new ArrayList<ServicioCatering>();
        }
    }
}
