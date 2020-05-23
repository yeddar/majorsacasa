package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.FranjaServicioLimpieza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FslDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void addFranja(FranjaServicioLimpieza fsl) {
        jdbcTemplate.update(
                "INSERT INTO franja_limpieza (nick_empresa, nick_demandante, dia_semana, h_ini, h_fin) VALUES(?,?,?,?,?)",
                fsl.getNick_empresa(), fsl.getNick_demandante(),
                fsl.getDiaSemana(), fsl.gethIni(), fsl.gethFin()
        );
    }

    public List<FranjaServicioLimpieza> getFranjasByDemandanteAndEmpresa(String nickDem, String nickEmp) {
        try {
            return jdbcTemplate.query("SELECT * FROM franja_limpieza where nick_demandante = '"+nickDem+"' and nick_empresa = '"+nickEmp+"'",
                    new FslRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<FranjaServicioLimpieza>();
        }
    }
}
