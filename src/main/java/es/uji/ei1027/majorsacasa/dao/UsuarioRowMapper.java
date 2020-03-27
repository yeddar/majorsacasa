package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.ROL_USUARIO;
import es.uji.ei1027.majorsacasa.model.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRowMapper implements RowMapper<Usuario> {
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Usuario usuario = new Usuario();

        usuario.setNick(rs.getString("nick"));
        usuario.setPass(rs.getString("pass"));
        usuario.setRol((ROL_USUARIO) rs.getObject("tipo"));

        return usuario;
    }
}
