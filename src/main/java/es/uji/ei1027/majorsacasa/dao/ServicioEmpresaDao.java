package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.ServicioEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServicioEmpresaDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addServicioEmpresa(ServicioEmpresa servicioEmpresa) throws DataAccessException {
        jdbcTemplate.update(
                "INSERT INTO servicio_empresa VALUES(?,?,?,?,?)",
                servicioEmpresa.getNick_empresa(), servicioEmpresa.getNick_demandante(), servicioEmpresa.getF_ini(),
                servicioEmpresa.getF_fin(), servicioEmpresa.getServ_status()
        );
    }

    public void deleteAsignacionEmpresa(ServicioEmpresa servicioEmpresa) {
        jdbcTemplate.update("DELETE FROM servicio_empresa WHERE nick_empresa=? AND nick_demandante=?",
                servicioEmpresa.getNick_empresa(), servicioEmpresa.getNick_demandante());
    }

    public void updateAsignacion(ServicioEmpresa servicioEmpresa) {
        jdbcTemplate.update("UPDATE servicio_empresa SET f_ini=?, f_fin=?, serv_status=?" +
                        " WHERE nick_empresa=? AND nick_demandante=?",
                servicioEmpresa.getF_ini(), servicioEmpresa.getF_fin(), servicioEmpresa.getServ_status(),
                servicioEmpresa.getNick_empresa(), servicioEmpresa.getNick_demandante()
        );
    }

    public List<ServicioEmpresa> getServiciosEmpresa() {
        try {
            return jdbcTemplate.query("SELECT * FROM servicio_empresa",
                    new ServicioEmpresaRowMapper());
        } catch (Exception e) {
            return new ArrayList<ServicioEmpresa>();
        }
    }

    public List<ServicioEmpresa> getServiciosEmpresaDemandante(String nick) {
        try{
            return jdbcTemplate.query("SELECT * FROM servicio_empresa WHERE nick_demandante = '"+nick+"'", new ServicioEmpresaRowMapper());
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public void setTypeStatus(String nickEmp, String nickDem, String status) {
        jdbcTemplate.update("UPDATE servicio_empresa SET serv_status=?" +
                        " WHERE nick_empresa=? AND nick_demandante=?",
                status, nickEmp, nickDem
        );
    }

    public ServicioEmpresa getServicioEmpresaStatus(String nickDem, String nickEmp) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM servicio_empresa WHERE nick_demandante='"+nickDem+"' and nick_empresa='"+nickEmp+"'",
                    new ServicioEmpresaRowMapper()
                    );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<ServicioEmpresa> getServiciosEmpresaByNickEmpresa(String nick_empresa) {
        try{
            return jdbcTemplate.query("SELECT * FROM servicio_empresa WHERE nick_empresa = '"+nick_empresa+"'", new ServicioEmpresaRowMapper());
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public void setF_Ini(String nickEmp, String nick) {
        jdbcTemplate.update("UPDATE servicio_empresa SET f_ini=?" +
                        " WHERE nick_empresa=? AND nick_demandante=?",
                LocalDate.now(), nickEmp, nick
        );
    }

    public void setF_Fin(String nickEmp, String nick) {
        jdbcTemplate.update("UPDATE servicio_empresa SET f_fin=?" +
                        " WHERE nick_empresa=? AND nick_demandante=?",
                LocalDate.now(), nickEmp, nick
        );
    }

    public List<ServicioEmpresa> getSinRevisarDemandante(String nickDem) {
        try{
            return jdbcTemplate.query("SELECT * FROM servicio_empresa WHERE nick_demandante = '"+nickDem+"' and serv_status = 'SIN EVALUAR'", new ServicioEmpresaRowMapper());
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
}
