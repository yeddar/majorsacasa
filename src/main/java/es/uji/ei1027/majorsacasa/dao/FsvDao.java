package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.FranjaServicioVoluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.util.List;

@Repository
public class FsvDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addFranjaServicioVoluntario(FranjaServicioVoluntario fsv) throws DataAccessException {
        String nick = fsv.getNick();
        String diaSemana = fsv.getDiaSemana();
        LocalTime hInicio = fsv.gethIni();
        LocalTime hFin = fsv.gethFin();

        jdbcTemplate.update(
                "INSERT INTO franja_voluntario(nick_voluntario,dia_semana,h_ini,h_fin) VALUES(?, ?, ?, ?)", nick, diaSemana,
                hInicio, hFin
        );
    }

    public void deleteFsv(FranjaServicioVoluntario fsv) {
        jdbcTemplate.update("DELETE FROM franja_voluntario WHERE id=?", fsv.getId());
    }

    public void deleteFsv(int id) {
        jdbcTemplate.update("DELETE FROM franja_voluntario WHERE id=?", id);
    }

    public FranjaServicioVoluntario getFsv(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM franja_voluntario WHERE id=?",
                    new FsvRowMapper(),
                    id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<FranjaServicioVoluntario> getFsvByNick(String nick_vol) {
        try {
            return jdbcTemplate.query("SELECT * FROM franja_voluntario WHERE nick_voluntario=?",
                    new FsvRowMapper(), nick_vol
                    );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    void updateFsv(FranjaServicioVoluntario fsv) {
        jdbcTemplate.update("UPDATE franja_voluntario SET nick=?, dia_semana=?, h_ini=?, h_fin=? WHERE id=?",
                fsv.getNick(), fsv.getDiaSemana(),
                fsv.gethIni(), fsv.gethFin(), fsv.getId()
        );
    }


    public List<FranjaServicioVoluntario> getFsvList() {
        return jdbcTemplate.query("SELECT * FROM franja_voluntario",
                new FsvRowMapper());
    }

    public List<FranjaServicioVoluntario> getFsvFree() {
        return jdbcTemplate.query("SELECT * FROM franja_voluntario WHERE id NOT IN (SELECT id_franja FROM asignacion_voluntario)",
                new FsvRowMapper());
    }

    public List<FranjaServicioVoluntario> getFsvFreeByVol(String nick) {
        return jdbcTemplate.query("SELECT * FROM franja_voluntario WHERE id NOT IN (SELECT id_franja FROM asignacion_voluntario) and nick_voluntario = '"+nick+"'",
                new FsvRowMapper());
    }

    public List<FranjaServicioVoluntario> getFsvOccupedByVol(String nick) {
        return jdbcTemplate.query("SELECT * FROM franja_voluntario WHERE id IN (SELECT id_franja FROM asignacion_voluntario) and nick_voluntario = '"+nick+"'",
                new FsvRowMapper());
    }


}
