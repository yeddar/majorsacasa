package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.PreferenciaVoluntario;
import es.uji.ei1027.majorsacasa.model.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PreferenciaVoluntarioDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addPreferenciaVoluntario(PreferenciaVoluntario preferenciaVoluntario) {
        jdbcTemplate.update("INSERT INTO preferencias_voluntario VALUES(?, ?)", preferenciaVoluntario.getNick(), preferenciaVoluntario.getPreferencia());
    }

    void deletePreferenciaVoluntario(PreferenciaVoluntario preferenciaVoluntario) {
        jdbcTemplate.update("DELETE FROM preferencias_voluntario WHERE nick=? AND preferencia=?", preferenciaVoluntario.getNick(), preferenciaVoluntario.getPreferencia());
    }

    PreferenciaVoluntario getPreferenciaVoluntarioPorVoluntario(Voluntario v){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM preferencias_voluntario WHERE nick=?",
                    new PreferenciaVoluntarioRowMapper(), v.getNick());
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    /*
    void updatePreferenciaVoluntario(PreferenciaVoluntario preferenciaVoluntario){
        jdbcTemplate .update( "UPDATE preferencias_voluntario SET precio=?" +
                        " WHERE nick=? AND id=?",
                asignacionEmpresa.getPrecio(), asignacionEmpresa.getNick(), asignacionEmpresa.getId());
    }
     */

    List<PreferenciaVoluntario> getAsignaciones(){
        try {
            return jdbcTemplate.query("SELECT * FROM preferencias_voluntario",
                    new PreferenciaVoluntarioRowMapper());
        }
        catch (Exception e) {
            return new ArrayList<>();
        }
    }




}
