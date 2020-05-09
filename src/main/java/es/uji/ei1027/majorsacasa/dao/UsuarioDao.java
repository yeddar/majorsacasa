package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Usuario;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Usuario> getUsuarios() {
        try {
            return jdbcTemplate.query("SELECT * FROM Usuario", new UsuarioRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Usuario>();
        }
    }

    public Usuario getUsuario(String nickUsuario) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Usuario WHERE nick=?", new UsuarioRowMapper(), nickUsuario);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public void addUsuario(Usuario u) throws DataAccessException {
        jdbcTemplate.update("INSERT INTO Usuario VALUES(?, ?, ?)",
                u.getNick(), u.getPass(), u.getRol()
        );
    }


    public void updateUsuario(Usuario u) {
        jdbcTemplate.update("UPDATE Usuario SET nick=?, pass=?, tipo=?" +
                        " WHERE nick=?",
                u.getPass(), u.getRol(), u.getNick());
    }


    void deleteUsuario(Usuario u) {
        jdbcTemplate.update("DELETE FROM Usuario WHERE nick=?", u.getNick());
    }


    public Usuario loadUserByNick(String nick, String password) {
        // Buscar usuario
        Usuario user = getUsuario(nick);
        if (user == null)
            return null; // Usuari no encontrado
        // Contraseña
        // TODO: Implementar cifrado de contraseñasen todos los usuarios (práctica 6)
//        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
//        if (passwordEncryptor.checkPassword(password, user.getPass())) {
        if (user.getPass().equals(password)) { // Provisional
            // TODO: Borrar password de forma segura
            return user;
        }
        else {
            return null; // bad login!
        }
    }


}
