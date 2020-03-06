package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.RespEmpresa;
import org.springframework.beans.factory.annotation. Autowired ;
import org.springframework.dao.DataAccessException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype. Repository ;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RespEmpresaDao {
    private JdbcTemplate jdbcTemplate ;
    private static String TABLA = "resp_empresa";
    private static String PKEY = "nick";
    private static String COL2 = "nombre";
    private static String COL3 = "cif";
    private static String COL4 = "tlf";
    private static String COL5 = "correo";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    void addRespEmpresa(RespEmpresa d) throws DataAccessException {
        jdbcTemplate.update(
                "INSERT INTO "+TABLA+" VALUES(?, ?, ?, ?, ?)",
                d.getNick(), d.getNombre(), d.getCif(), d.getTlf(), d.getCorreo()
        );
    }

    void deleteRespEmpresa (RespEmpresa d) {
        jdbcTemplate.update("DELETE FROM "+TABLA+" WHERE "+PKEY+"=?", d.getNick());
    }

    public RespEmpresa getRespEmpresa(String nickRespEmpresa) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM "+TABLA+" WHERE "+PKEY+"=?",
                    new RespEmpresaRowMapper(),
                    nickRespEmpresa);
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    void updateRespEmpresa(RespEmpresa n) {
        jdbcTemplate .update( "UPDATE "+TABLA+" SET "+COL2+"=?, "+COL3+"=?, "+COL4+"=?, "
                +COL5+"=? WHERE "+PKEY+"=?", n.getNombre(), n.getCif(), n.getTlf(),
                n.getCorreo(), n.getNick());
    }

    public List<RespEmpresa> getRespsEmpresas() {
        try {
            return jdbcTemplate.query("SELECT * FROM "+TABLA,
                    new RespEmpresaRowMapper());
        }
        catch (Exception e) {
            return new ArrayList<RespEmpresa>();
        }
    }
}
