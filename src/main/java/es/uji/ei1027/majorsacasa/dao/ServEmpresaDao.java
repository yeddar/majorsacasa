package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.ServEmpresa;
import org.springframework.beans.factory.annotation. Autowired ;
import org.springframework.dao.DataAccessException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype. Repository ;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServEmpresaDao {
    private JdbcTemplate jdbcTemplate ;
    private static String TABLA = "serv_empresa";
    private static String PKEY = "id";
    private static String COL2 = "resp_empresa_nick";
    private static String COL3 = "tipo_serv_emp";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    void addServEmpresa(ServEmpresa d) throws DataAccessException {
        jdbcTemplate.update(
                "INSERT INTO "+TABLA+" VALUES(?, ?, ?)",
                d.getId(), d.getRespEmpresaNick(), d.getTipoServEmp()
        );
    }

    void deleteServEmpresa (ServEmpresa d) {
        jdbcTemplate.update("DELETE FROM "+TABLA+" WHERE "+PKEY+"=?", d.getId());
    }

    public ServEmpresa getServEmpresa(String nickServEmpresa) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM "+TABLA+" WHERE "+PKEY+"=?",
                    new ServEmpresaRowMapper(),
                    nickServEmpresa);
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    void updateServEmpresa(ServEmpresa n) {
        jdbcTemplate .update( "UPDATE "+TABLA+" SET "+COL2+"=?, "+COL3+"=?" +
                " WHERE "+PKEY+"=?", n.getRespEmpresaNick(), n.getTipoServEmp(), n.getId());
    }

    public List<ServEmpresa> getServsEmpresas() {
        try {
            return jdbcTemplate.query("SELECT * FROM "+TABLA,
                    new ServEmpresaRowMapper());
        }
        catch (Exception e) {
            return new ArrayList<ServEmpresa>();
        }
    }
}
