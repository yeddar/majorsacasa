package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VoluntarioDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addVoluntaio(Voluntario v) throws DataAccessException {
        jdbcTemplate.update("INSERT INTO Voluntario VALUES(?, ?, ?, ?, ?, ?)", v.getNick(),
                v.getNombre(), v.getEdad(), v.getTlf(), v.getCorreo(), v.isEsActivo()
        );
    }

    public void deleteVoluntario(String nick) {
        jdbcTemplate.update("DELETE FROM Voluntario WHERE nick=?", nick);
    }

    public void updateVoluntario(Voluntario v) {
        String nick = v.getNick();
        String nombre = v.getNombre();
        int edad = v.getEdad();
        String tlf = v.getTlf();
        String correo = v.getCorreo();
        boolean esActivo = v.isEsActivo();
        jdbcTemplate.update("UPDATE Voluntario SET nombre = ?, edad = ?, tlf = ?, correo = ?, es_activo = ? WHERE nick ='"+nick+"'",
                nombre, edad, tlf, correo, esActivo);
    }

    public Voluntario getVoluntario(String nickVoluntario) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Voluntario WHERE nick='"+nickVoluntario+"'", new VoluntarioRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Voluntario> getVoluntarios() {
        try {
            return jdbcTemplate.query("SELECT * FROM Voluntario", new VoluntarioRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Voluntario>();
        }
    }
}
