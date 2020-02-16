package es.uji.ei1027.majorsacasa.dao;
import es.uji.ei1027.majorsacasa.model.Demandante;
import org.springframework.beans.factory.annotation. Autowired ;
import org.springframework.dao.DataAccessException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype. Repository ;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository
public class DemandanteDao {
    private JdbcTemplate jdbcTemplate ;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    void addDemantante(Demandante d) throws DataAccessException {
            jdbcTemplate.update(
                    "INSERT INTO Demandante VALUES(?, ?, ?, ?, ?, ?, ?)",
                    d.getNick(), d.getPass(), d.getNombre(), d.getEdad(), d.getTlf(), d.getCod_asist(), d.getCorreo()
            );
    }

    void deleteDemandante (Demandante d) {
        jdbcTemplate.update("DELETE FROM Demandante WHERE nick=?", d.getNick());
    }

    public Demandante getDemandante(String nickDemandante) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Demandante WHERE nick=?",
                    new DemandanteRowMapper(),
                    nickDemandante);
        }
        catch (EmptyResultDataAccessException e) {
            return null ;
        }
    }

    void updateDemandante(Demandante n) {
        jdbcTemplate .update( "UPDATE Demandante SET nombre=?, edad=?, tlf=?, cod_asist=?, correo=?" +
                " WHERE nick=?", n.getNombre(), n.getEdad(), n.getTlf(), n.getCod_asist(), n.getCorreo(), n.getNick());
    }


    public List<Demandante> getDemandantes() {
        try {
            return jdbcTemplate.query("SELECT * FROM Demandante",
                    new DemandanteRowMapper());
        }
        catch (Exception e) {
            return new ArrayList<Demandante>();
        }
    }








}
