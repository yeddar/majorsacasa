package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.FranjaServicioLimpieza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class FslDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void addFranja(FranjaServicioLimpieza fsl) {
        jdbcTemplate.update(
                "INSERT INTO franja_limpieza VALUES(?,?,?,?,?,?)",
                fsl.getId_franja(), fsl.getNick_empresa(), fsl.getNick_demandante(),
                fsl.getDiaSemana(), fsl.gethIni(), fsl.gethFin()
        );
    }
}
