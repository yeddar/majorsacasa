package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.ServicioEmpresa;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ServicioEmpresaRowMapper implements RowMapper<ServicioEmpresa> {
    @Override
    public ServicioEmpresa mapRow(ResultSet rs, int rowNum) throws SQLException {
        ServicioEmpresa asignacionEmpresa = new ServicioEmpresa();
        asignacionEmpresa.setNick_demandante(rs.getString("nick_demandante"));
        asignacionEmpresa.setNick_empresa(rs.getString("nick_empresa"));
        asignacionEmpresa.setF_ini(rs.getObject("f_ini", LocalDate.class));
        asignacionEmpresa.setF_fin(rs.getObject("f_fin", LocalDate.class));
        asignacionEmpresa.setServ_status(rs.getString("serv_status"));
        return asignacionEmpresa;
    }
}
