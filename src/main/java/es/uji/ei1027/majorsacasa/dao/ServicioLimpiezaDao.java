package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.ServicioLimpieza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServicioLimpiezaDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addServicioLimpieza(ServicioLimpieza servicioLimpieza){
        jdbcTemplate.update(
                "INSERT INTO servicio_limpieza VALUES(?, ?, ?)", servicioLimpieza.getNick_empresa(),
                servicioLimpieza.getNick_demandante(), servicioLimpieza.getHoras()
        );
    }

    public void deleteServicioLimpieza(ServicioLimpieza servicioLimpieza) {
        jdbcTemplate.update("DELETE FROM servicio_limpieza WHERE nick_empresa=? AND nick_demandante=?",
                servicioLimpieza.getNick_empresa(), servicioLimpieza.getNick_demandante());
    }

    public void updateServicioLimpieza(ServicioLimpieza servicioLimpieza) {
        jdbcTemplate.update("UPDATE servicio_limpieza SET cant_horas=?" +
                        " WHERE nick_empresa=? AND nick_demandante=?",
                servicioLimpieza.getHoras(), servicioLimpieza.getNick_empresa(), servicioLimpieza.getNick_demandante()
        );
    }

    public List<ServicioLimpieza> getServiciosLimpieza() {
        try {
            return jdbcTemplate.query("SELECT * FROM servicio_limpieza",
                    new ServicioLimpiezaRowMapper());
        } catch (Exception e) {
            return new ArrayList<ServicioLimpieza>();
        }
    }
}
