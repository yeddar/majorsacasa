package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.ServicioSanitario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServicioSanitarioDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addServicioSanitario(ServicioSanitario servicioSanitario){
        jdbcTemplate.update(
                "INSERT INTO servicio_sanitario VALUES(?, ?, ?, ?, ?)", servicioSanitario.getNick_empresa(),
                servicioSanitario.getNick_demandante(), servicioSanitario.getNecesidad(), servicioSanitario.getDia_visita(),
                servicioSanitario.getManana_tarde()
        );
    }

    public void deleteServicioSanitario(ServicioSanitario servicioSanitario) {
        jdbcTemplate.update("DELETE FROM servicio_sanitario WHERE nick_empresa=? AND nick_demandante=?",
                servicioSanitario.getNick_empresa(), servicioSanitario.getNick_demandante());
    }

    public void updateServicioSanitario(ServicioSanitario servicioSanitario) {
        jdbcTemplate.update("UPDATE servicio_sanitario SET necesidad=?, dia_visita=?, manana_tarde=?" +
                        " WHERE nick_empresa=? AND nick_demandante=?",
                servicioSanitario.getNecesidad(), servicioSanitario.getDia_visita(), servicioSanitario.getManana_tarde(),
                servicioSanitario.getNick_empresa(), servicioSanitario.getNick_demandante()
        );
    }

    public List<ServicioSanitario> getServiciosSanitario() {
        try {
            return jdbcTemplate.query("SELECT * FROM servicio_sanitario",
                    new ServicioSanitarioRowMapper());
        } catch (Exception e) {
            return new ArrayList<ServicioSanitario>();
        }
    }

    public ServicioSanitario getServicioSanitario(String nickEmp, String nickDem) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM servicio_sanitario WHERE nick_demandante='"+nickDem+"' and nick_empresa='"+nickEmp+"'",
                    new ServicioSanitarioRowMapper()
                    );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<ServicioSanitario> getAsignaciones(String nick_empresa) {
        try {
            return jdbcTemplate.query("SELECT * FROM servicio_sanitario WHERE nick_empresa = '"+nick_empresa+"'",
                    new ServicioSanitarioRowMapper());
        } catch (Exception e) {
            return new ArrayList<ServicioSanitario>();
        }
    }

    public void setFeedback(String nickEmp, String nick, LocalDate dia_visita, char franja_dia) {
        jdbcTemplate.update("UPDATE servicio_sanitario SET dia_visita=?, manana_tarde=?" +
                        " WHERE nick_empresa=? AND nick_demandante=?",
                dia_visita, franja_dia, nickEmp, nick
        );
    }

    public ServicioSanitario getServicioSanitarioByDemandante(String nickDem) {
        try {
            return jdbcTemplate.queryForObject("SELECT * "+
                            "FROM servicio_sanitario " +
                            "WHERE nick_demandante='"+nickDem+"'",
                    new ServicioSanitarioRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
