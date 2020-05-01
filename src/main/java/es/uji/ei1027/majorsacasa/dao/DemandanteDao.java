package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Demandante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository
public class DemandanteDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // List method

    public List<Demandante> getDemandantes() {
        try {
            return jdbcTemplate.query("SELECT * FROM Demandante",
                    new DemandanteRowMapper());
        } catch (Exception e) {
            return new ArrayList<Demandante>();
        }
    }

    // Add method

    public void addDemandante(Demandante d) throws DataAccessException {
        jdbcTemplate.update(
                "INSERT INTO Demandante VALUES(?, ?, ?,  ?, ?, ?,  ?, ?)",
                d.getNick(), d.getNombre(), d.getEdad(),
                d.getTlf(), d.getCorreo(), d.getDireccion(),
                d.getCod_asist(), d.getStatus()
        );
    }

    // getByNick method

    public Demandante getDemandante(String nickDemandante) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Demandante WHERE nick=?",
                    new DemandanteRowMapper(),
                    nickDemandante);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Update method

    public void updateDemandante(Demandante d) {
        jdbcTemplate.update("UPDATE Demandante SET nombre=?, edad=?, tlf=?, correo=?, " +
                        "direccion=?, cod_asist=?, es_activo=?" + " WHERE nick=?",
                d.getNombre(), d.getEdad(),
                d.getTlf(), d.getCorreo(), d.getDireccion(),
                d.getCod_asist(), d.getStatus(),
                d.getNick());
    }


    // Delete method

    public void deleteDemandante(String nick) {
        jdbcTemplate.update("DELETE FROM Demandante WHERE nick=?", nick);
    }


}
