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
                d.getTlf(), d.getEmail(), d.getDireccion(),
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
        jdbcTemplate.update("UPDATE Demandante SET nombre=?, edad=?, tlf=?, email=?, " +
                        "direccion=?, cod_asist=?, status=?" + " WHERE nick=?",
                d.getNombre(), d.getEdad(),
                d.getTlf(), d.getEmail(), d.getDireccion(),
                d.getCod_asist(), d.getStatus(),
                d.getNick());
    }


    // Delete method

    public void deleteDemandante(String nick) {
        // PRIMERO PASAMOS EL ESTADO DEL CLIENTE A CANCELADO
        jdbcTemplate.update("UPDATE demandante SET status = 'CANCELADO' WHERE nick = '"+nick+"'");
        // Y DESPUES PASAMOS A CANCELADO LOS SERVICIOS QUE ÉSTE TENÍA
        // DE PAGO
        jdbcTemplate.update("UPDATE servicio_empresa SET serv_status = 'CANCELADO' WHERE nick_demandante = '"+nick+"'");
        // DE VOLUNTARIADO
        jdbcTemplate.update("UPDATE asignacion_voluntario SET serv_status = 'CANCELADO' WHERE nick_demandante = '"+nick+"'");
    }

    public List<Demandante> getDemandantesPendientes() {
        try {
            return jdbcTemplate.query("SELECT * FROM Demandante WHERE status ='SIN REVISAR'",
                    new DemandanteRowMapper());
        } catch (Exception e) {
            return new ArrayList<Demandante>();
        }
    }

    public List<Demandante> getDemandantesAceptados(){
        try {
            return jdbcTemplate.query("SELECT * FROM Demandante WHERE status ='ACEPTADO'",
                    new DemandanteRowMapper());
        } catch (Exception e) {
            return new ArrayList<Demandante>();
        }
    }

    public void acceptDemandante(String nick) {
        jdbcTemplate.update("UPDATE demandante SET status = 'ACEPTADO' WHERE nick = '"+nick+"'");
    }
}
