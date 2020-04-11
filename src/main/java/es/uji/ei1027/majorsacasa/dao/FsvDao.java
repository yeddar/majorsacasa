package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.FranjaServicioVoluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FsvDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addFranjaServicioVoluntario(FranjaServicioVoluntario fsv) throws DataAccessException {
        int id = fsv.getId();
        String nick = fsv.getNick();
        String diaSemana = fsv.getDiaSemana();
        LocalTime hInicio = fsv.gethIni();
        LocalTime hFin = fsv.gethFin();

        jdbcTemplate.update(
                "INSERT INTO franja_servicio_voluntario VALUES(?, ?, ?, ?, ?)",
                id, nick, diaSemana,
                hInicio, hFin
        );
    }

    public void deleteFsv(FranjaServicioVoluntario fsv) {
        jdbcTemplate.update("DELETE FROM franja_servicio_voluntario WHERE if=?", fsv.getId());
    }

    public FranjaServicioVoluntario getFsv(String id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM FranjaServicioVoluntario WHERE id=?",
                    new FsvRowMapper(),
                    id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    void updateFsv(FranjaServicioVoluntario fsv) {
        jdbcTemplate.update("UPDATE franja_servicio_voluntario SET nick=?, dia_semana=?, h_ini=?, h_fin=? WHERE id=?",
                fsv.getNick(), fsv.getDiaSemana(),
                fsv.gethIni(), fsv.gethFin(), fsv.getId()
        );
    }


    public List<FranjaServicioVoluntario> getFsvList() {
        try {
            return jdbcTemplate.query("SELECT * FROM franja_servicio_voluntario",
                    new FsvRowMapper());
        } catch (Exception e) {
            return new ArrayList<FranjaServicioVoluntario>();
        }
    }


}
