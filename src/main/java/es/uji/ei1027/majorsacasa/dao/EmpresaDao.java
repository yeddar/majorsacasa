package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmpresaDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addEmpresa(Empresa d) throws DataAccessException {
        jdbcTemplate.update(
                "INSERT INTO Empresa VALUES(?, ?, ?, ?, ?,?,?)",
                d.getNick(), d.getNombre(), d.getNif(),
                d.getTlf(), d.getEmail(), d.getTipo_empresa(),
                d.getVacantes()
        );
    }

    public void deleteEmpresa(String nick) {
        jdbcTemplate.update("DELETE FROM Empresa WHERE nick=?" , nick);
    }

    public Empresa getEmpresa(String nickEmpresa) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Empresa WHERE nick=?",
                    new EmpresaRowMapper(),
                    nickEmpresa);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void updateEmpresa(Empresa d) {
        jdbcTemplate.update("UPDATE Empresa SET nombre=?, nif=?, tlf=?," +
                        "email=?, tipo_empresa=?, vacantes=?" +
                        "WHERE nick=?",
                d.getNombre(), d.getNif(), d.getTlf(),
                d.getEmail(), d.getTipo_empresa(), d.getVacantes(),
                d.getNick());
    }

    public List<Empresa> getEmpresas() {
        try {
            return jdbcTemplate.query("SELECT * FROM Empresa",
                    new EmpresaRowMapper());
        } catch (Exception e) {
            return new ArrayList<Empresa>();
        }
    }

    public List<Empresa> getEmpresaByType(String tipo_empresa) {
        try {
            return jdbcTemplate.query("SELECT * FROM Empresa WHERE tipo_empresa = '"+tipo_empresa+"'",
                    new EmpresaRowMapper());
        } catch (Exception e) {
            return new ArrayList<Empresa>();
        }
    }
}
