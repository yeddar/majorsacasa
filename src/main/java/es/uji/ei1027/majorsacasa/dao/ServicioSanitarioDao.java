package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.ServicioSanitario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServicioSanitarioDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    void addServicioSanitario(ServicioSanitario servicioSanitario){
        jdbcTemplate.update(
                "INSERT INTO servicio_sanitario VALUES(?, ?, ?, ?, ?)", servicioSanitario.getNick_empresa(),
                servicioSanitario.getNick_demandante(), servicioSanitario.getNecesidad(), servicioSanitario.getDia_visita(),
                servicioSanitario.getManana_tarde()
        );
    }

    void deleteServicioSanitario(ServicioSanitario servicioSanitario) {
        jdbcTemplate.update("DELETE FROM servicio_sanitario WHERE nick_empresa=? AND nick_demandante=?",
                servicioSanitario.getNick_empresa(), servicioSanitario.getNick_demandante());
    }

    void updateServicioSanitario(ServicioSanitario servicioSanitario) {
        jdbcTemplate.update("UPDATE servicio_catering SET necesidad=?, dia_visita=?, manana_tarde=?" +
                        " WHERE nick_empresa=? AND nick_demandante=?",
                servicioSanitario.getNecesidad(), servicioSanitario.getDia_visita(), servicioSanitario.getManana_tarde(),
                servicioSanitario.getNick_empresa(), servicioSanitario.getNick_demandante()
        );
    }

    List<ServicioSanitario> getServiciosSanitario() {
        try {
            return jdbcTemplate.query("SELECT * FROM servicio_sanitario",
                    new ServicioSanitarioRowMapper());
        } catch (Exception e) {
            return new ArrayList<ServicioSanitario>();
        }
    }
}