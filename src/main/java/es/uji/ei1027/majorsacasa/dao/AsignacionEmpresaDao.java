package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.AsignacionEmpresa;
import es.uji.ei1027.majorsacasa.model.Demandante;
import es.uji.ei1027.majorsacasa.model.RespEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AsignacionEmpresaDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    void addAsignacionEmpresa(AsignacionEmpresa asignacionEmpresa) throws DataAccessException {
        jdbcTemplate.update(
                "INSERT INTO asignacion_empresa VALUES(?, ?, ?)",
                asignacionEmpresa.getNick(), asignacionEmpresa.getId(), asignacionEmpresa.getPrecio()
        );
    }

    void deleteAsignacionEmpresa(AsignacionEmpresa asignacionEmpresa) {
        jdbcTemplate.update("DELETE FROM asignacion_empresa WHERE id=? AND nick=?", asignacionEmpresa.getId(), asignacionEmpresa.getNick());
    }

    AsignacionEmpresa getAsignacionEmpresaPorDemandante(Demandante d) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM asignacion_empresa WHERE nick=?",
                    new AsignacionEmpresaRowMapper(), d.getNick());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /*
    AsignacionEmpresa getAsignacionEmpresaPorEmpresa(RespEmpresa e){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM asignacion_empresa WHERE nick=?",
                    new DemandanteRowMapper(),
                    nickDemandante);
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

     */

    void updateAsignacion(AsignacionEmpresa asignacionEmpresa) {
        jdbcTemplate.update("UPDATE asignacion_empresa SET precio=?" +
                        " WHERE nick=? AND id=?",
                asignacionEmpresa.getPrecio(), asignacionEmpresa.getNick(), asignacionEmpresa.getId());
    }

    List<AsignacionEmpresa> getAsignaciones() {
        try {
            return jdbcTemplate.query("SELECT * FROM asignacion_empresa",
                    new AsignacionEmpresaRowMapper());
        } catch (Exception e) {
            return new ArrayList<AsignacionEmpresa>();
        }
    }


}
