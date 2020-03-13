package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.FranjaServicioEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FranjaServicioEmpresaDao {
    private JdbcTemplate jdbcTemplate;
    private static String TABLA = "franja_servicio_emp";
    private static String PKEY = "id";
    private static String COL2 = "serv_empresa_id";
    private static String COL3 = "dia_semana";
    private static String COL4 = "h_ini";
    private static String COL5 = "h_fin";
    private static String COL6 = "vacantes";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    void addFranjaServicioEmpresa(FranjaServicioEmpresa d) throws DataAccessException {
        jdbcTemplate.update(
                "INSERT INTO " + TABLA + " VALUES(?, ?, ?, ?, ?, ?)",
                d.getId(), d.getServEmpresaId(), d.getDiaSemana(), d.gethIni(), d.gethFin(), d.getVacantes()
        );
    }

    void deleteFranjaServicioEmpresa(FranjaServicioEmpresa d) {
        jdbcTemplate.update("DELETE FROM " + TABLA + " WHERE " + PKEY + "=?", d.getId());
    }

    public FranjaServicioEmpresa getFranjaServicioEmpresa(String nickFranjaServicioEmpresa) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM " + TABLA + " WHERE " + PKEY + "=?",
                    new FranjaServicioEmpresaRowMapper(),
                    nickFranjaServicioEmpresa);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    void updateFranjaServicioEmpresa(FranjaServicioEmpresa n) {
        jdbcTemplate.update("UPDATE " + TABLA + " SET " + COL2 + "=?, " + COL3 + "=?, " + COL4 + "=?, "
                        + COL5 + "=?, " + COL6 + "=?" + " WHERE " + PKEY + "=?",
                n.getServEmpresaId(), n.getDiaSemana(), n.gethIni(), n.gethFin(), n.getVacantes());
    }

    public List<FranjaServicioEmpresa> getServsEmpresas() {
        try {
            return jdbcTemplate.query("SELECT * FROM " + TABLA,
                    new FranjaServicioEmpresaRowMapper());
        } catch (Exception e) {
            return new ArrayList<FranjaServicioEmpresa>();
        }
    }
}
