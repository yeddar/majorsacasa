package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.*;
import es.uji.ei1027.majorsacasa.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/empresa")
public class EmpresaController {
    private EmpresaDao empresaDao;
    private UsuarioDao usuarioDao;
    private DemandanteDao demandanteDao;
    private ServicioEmpresaDao servEmpresaDao;
    private ServicioCateringDao servCatDao;
    private ServicioLimpiezaDao servLimDao;
    private ServicioSanitarioDao servSanDao;

    @Autowired
    public void setEmpresaDao(EmpresaDao empresaDao) {
        this.empresaDao = empresaDao;
    }

    @Autowired
    public void setDemandanteDao(DemandanteDao demandanteDao) {
        this.demandanteDao = demandanteDao;
    }

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Autowired
    public void setServEmpresaDao(ServicioEmpresaDao servEmpresaDao) {
        this.servEmpresaDao = servEmpresaDao;
    }

    @Autowired
    public void setServCatDao(ServicioCateringDao servCatDao) {
        this.servCatDao = servCatDao;
    }

    @Autowired
    public void setServLimDao(ServicioLimpiezaDao servLimDao) {
        this.servLimDao = servLimDao;
    }

    @Autowired
    public void setServSanDao(ServicioSanitarioDao servSanDao) {
        this.servSanDao = servSanDao;
    }

    // List methods

    @RequestMapping("/list")
    public String listEmpresas(Model model, HttpSession session) {
        session.setAttribute("lastURL", "../list");
        model.addAttribute("empresas", empresaDao.getEmpresas());
        return "empresa/list";
    }

    @RequestMapping("/listAsignaciones")
    public String listAsignaciones(Model model, HttpSession session){
        session.setAttribute("lastURL", "../listAsignaciones");
        // Cargar de bbdd todas las asignaciones de la empresa que esten en aceptado
        String nick_empresa = (String) session.getAttribute("nick");
        model.addAttribute("asignaciones_empresa", servEmpresaDao.getServiciosEmpresaByNickEmpresa(nick_empresa));
        // COGEMOS EL TIPO DE EMPRESA QUE ES
        String tipoEmpresa = empresaDao.getEmpresa(nick_empresa).getTipo_empresa();
        model.addAttribute("tipo_empresa", tipoEmpresa);
        // clave demandante, valor asignacion
        HashMap asignaciones = new HashMap<>();
        if(tipoEmpresa.equals("CATERING")){
            // SI ES DE CATERING COGEMOS DATOS DE LA TABLA CATERING
            List<ServicioCatering> asig_catering = servCatDao.getAsignaciones(nick_empresa);
            for(ServicioCatering servicio : asig_catering){
                asignaciones.put(servicio.getNick_demandante(), servicio);
            }
            model.addAttribute("asignacion", asignaciones);
        }else if (tipoEmpresa.equals("SANITARIA")){
            // SI ES DE SANITARIO COGEMOS DATOS DE LA TABLA SANITARIO
            List<ServicioSanitario> asig_sanitario = servSanDao.getAsignaciones(nick_empresa);
            for(ServicioSanitario servicio : asig_sanitario){
                asignaciones.put(servicio.getNick_demandante(), servicio);
            }
            model.addAttribute("asignacion", asignaciones);
        }else {
            // SI ES DE LIMPIEZA COGEMOS DATOS DE LA TABLA LIMPIEZA
            List<ServicioLimpieza> asig_limpieza = servLimDao.getAsignaciones(nick_empresa);
            for(ServicioLimpieza servicio : asig_limpieza){
                asignaciones.put(servicio.getNick_demandante(), servicio);
            }
            model.addAttribute("asignacion", asignaciones);
        }
        return "empresa/viewDemandantes";
    }

    @RequestMapping(value = "/listPendientes")
    public String listPendientes(Model model, HttpSession session){
        session.setAttribute("lastURL", "../listPendientes");
        String nick_empresa = (String) session.getAttribute("nick");
        // Coger de la base de datos todas las asignaciones a la empresa que esten en estado ESPERA EMPRESA (
        List<ServicioEmpresa> servEmpresa = servEmpresaDao.getServiciosEmpresaByNickEmpresa(nick_empresa);
        model.addAttribute("serviciosEmpresa", servEmpresa);
        // COGEMOS EL TIPO DE EMPRESA QUE ES
        String tipoEmpresa = empresaDao.getEmpresa(nick_empresa).getTipo_empresa();
        model.addAttribute("tipo_empresa", tipoEmpresa);
        // Recoger en un map con clave nick_demandante, todos los objetos demandante
        HashMap servicios = new HashMap<>();
        HashMap serv_espec = new HashMap();

        for(ServicioEmpresa serv : servEmpresa){
            Demandante dem = demandanteDao.getDemandante(serv.getNick_demandante());
            servicios.put(serv.getNick_demandante(), dem);
            // Recoger los datos especificos del servicio
            if(tipoEmpresa.equals("CATERING")){
                ServicioCatering servCat = servCatDao.getServicioCatering(nick_empresa, serv.getNick_demandante());
                serv_espec.put(serv.getNick_demandante(), servCat);
            }else if(tipoEmpresa.equals("SANITARIA")){
                ServicioSanitario servSan = servSanDao.getServicioSanitario(nick_empresa, serv.getNick_demandante());
                serv_espec.put(serv.getNick_demandante(), servSan);
            }else{
                ServicioLimpieza servLim = servLimDao.getServicioLimpieza(nick_empresa, serv.getNick_demandante());
                serv_espec.put(serv.getNick_demandante(), servLim);
            }
        }
        model.addAttribute("servicios", servicios);
        model.addAttribute("servEspec", serv_espec);

        return "empresa/viewPendientes";
    }

    // Add methods

    @RequestMapping(value = "/add")
    public String addEmpresa(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresa/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("empresa") Empresa empresa,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "empresa/add";

        Usuario user = new Usuario();
        user.setNick(empresa.getNick());
        user.setPass("123");
        user.setRol(ROL_USUARIO.EMPRESA);

        usuarioDao.addUsuario(user);
        System.out.print(empresa.toString());

        empresaDao.addEmpresa(empresa);

        return "redirect:/";
    }

    // Update methods

    @RequestMapping(value = "/update/{nick}", method = RequestMethod.GET)
    public String editEmpresa(Model model, @PathVariable String nick) {
        model.addAttribute("empresa", empresaDao.getEmpresa(nick));
        return "empresa/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("empresa") Empresa empresa,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "empresa/update";
        empresaDao.updateEmpresa(empresa);
        return "redirect:list";
    }

    // Delete method

    @RequestMapping(value = "/delete/{nick}")
    public String processDelete(@PathVariable String nick) {
        empresaDao.deleteEmpresa(nick);
        return "redirect:../list";
    }

    // Cancel method
    @RequestMapping(value = "/cancel/{nick}")
    public String cancelState(@PathVariable String nick, HttpSession session){
        servEmpresaDao.setTypeStatus((String) session.getAttribute("nick"), nick, "CANCELADO");
        servEmpresaDao.setF_Fin((String) session.getAttribute("nick"),nick);
        return "redirect:"+session.getAttribute("lastURL");
    }
}
