package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.AsignacionVoluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AsignacionVoluntarioDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /*
        CRUD BASICO DE ASIGNACION DE VOLUNTARIO
     */
    public void addAsignacionVoluntario(AsignacionVoluntario asignacionVoluntario) {
        jdbcTemplate.update("INSERT INTO asignacion_voluntario VALUES(?, ?, ?, ?, ?, ?)",
                asignacionVoluntario.getId_franja(), asignacionVoluntario.getNick_demandante(), asignacionVoluntario.getF_ini(),
                asignacionVoluntario.getF_fin(), asignacionVoluntario.getServ_status(), asignacionVoluntario.getFeedback_voluntario());
    }

    void deleteAsignacionVoluntario(AsignacionVoluntario asignacionVoluntario) {
        jdbcTemplate.update("DELETE FROM asignacion_voluntario WHERE id_franja=?", asignacionVoluntario.getId_franja());
    }

    List<AsignacionVoluntario> getAsignaciones() {
        try {
            return jdbcTemplate.query("SELECT * FROM asignacion_voluntario",
                    new AsignacionVoluntarioRowMapper());
        } catch (Exception e) {
            return new ArrayList<AsignacionVoluntario>();
        }
    }


    /*
    AsignacionVoluntario getAsignacionVoluntarioPorDemandante(Demandante d) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM asignacion_voluntario WHERE nick=?",
                    new AsignacionVoluntarioRowMapper(), d.getNick(empresa.getNick()));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

     */

    /*
    AsignacionVoluntario getAsignacionVoluntarioPorVoluntario(Voluntario v){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM asignacion_voluntario WHERE nick=?",
                    new DemandanteRowMapper(),
                    nickDemandante);
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    void updateAsignacionVoluntario(AsignacionVoluntario asignacionVoluntario){
        jdbcTemplate .update( "UPDATE asignacion_voluntario SET precio=?" +
                        " WHERE nick=? AND id=?",
                asignacionEmpresa.getPrecio(), asignacionEmpresa.getNick(), asignacionEmpresa.getId());
    }
     */



    public List<AsignacionVoluntario> getServiciosVoluntarioDemandante(String nick) {
        try {
            return jdbcTemplate.query("SELECT * FROM asignacion_voluntario WHERE nick_demandante = '"+nick+"'",
                    new AsignacionVoluntarioRowMapper());
        } catch (Exception e) {
            return new ArrayList<AsignacionVoluntario>();
        }
    }

    public void setTypeStatus(int idFranja, String status) {
        jdbcTemplate.update("UPDATE asignacion_voluntario SET serv_status=?" +
                        "WHERE id_franja=?",
                status, idFranja);
    }


    /*
        OBTENCIONES ESPECIALES
     */

    public AsignacionVoluntario getByFranja(int id_franja){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM asignacion_voluntario WHERE id_franja = ?",
                    new AsignacionVoluntarioRowMapper(),
                    id_franja);
        } catch (Exception e) {
            return new AsignacionVoluntario();
        }



    }

    public List<AsignacionVoluntario> getEsperaVoluntario(String id_voluntario){
        try {
            return jdbcTemplate.query( "SELECT id_franja, nick_demandante, f_ini, f_fin, serv_status, feedback_voluntario\n" +
                            "FROM asignacion_voluntario JOIN franja_voluntario\n" +
                            "ON (asignacion_voluntario.id_franja = franja_voluntario.id)\n" +
                            "WHERE serv_status=? AND nick_voluntario = ?;",
                    new AsignacionVoluntarioRowMapper(),
                    "ESPERA VOLUNTARIO",
                    id_voluntario);
        }
        catch (Exception e){
            return new ArrayList<AsignacionVoluntario>();
        }

    }


}
