package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.FranjaServicioVoluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FranjaServicioVoluntarioDao {
    private JdbcTemplate jdbcTemplate ;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    void addFranjaServicioVoluntario(FranjaServicioVoluntario fsv) throws DataAccessException {
        jdbcTemplate.update(
                "INSERT INTO FranjaServicioVoluntario VALUES(?, ?, ?, ?, ?)",
                fsv.getId(), fsv.getNick(), fsv.getDiaSemana(),
                fsv.gethIni(),  fsv.gethFin()
        );
    }

    void deleteDemandante (FranjaServicioVoluntario fsv) {
        jdbcTemplate.update("DELETE FROM FranjaServicioVoluntario WHERE if=?", fsv.getId());
    }

    public FranjaServicioVoluntario getDemandante(String id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM FranjaServicioVoluntario WHERE id=?",
                    new FranjaServicioVoluntarioRowMapper(),
                    id);
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    void updateFranjaServicioVoluntario(FranjaServicioVoluntario fsv) {
        jdbcTemplate.update("UPDATE FranjaServicioVoluntario SET nick=?, dia_semana=?, h_ini=?, h_fin=? WHERE id=?",
                fsv.getNick(), fsv.getDiaSemana(),
                fsv.gethIni(), fsv.gethFin(), fsv.getId()
        );
    }


    public List<FranjaServicioVoluntario> getFranjaServicioVoluntario() {
        try {
            return jdbcTemplate.query("SELECT * FROM FranjaServicioVoluntario",
                    new FranjaServicioVoluntarioRowMapper());
        }
        catch (Exception e) {
            return new ArrayList<FranjaServicioVoluntario>();
        }
    }








}