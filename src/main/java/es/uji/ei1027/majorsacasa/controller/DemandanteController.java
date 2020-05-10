package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.AsignacionVoluntarioDao;
import es.uji.ei1027.majorsacasa.dao.DemandanteDao;
import es.uji.ei1027.majorsacasa.dao.ServicioEmpresaDao;
import es.uji.ei1027.majorsacasa.dao.UsuarioDao;
import es.uji.ei1027.majorsacasa.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/demandante")
public class DemandanteController {
    private DemandanteDao demandanteDao;
    private UsuarioDao usuarioDao;
    private ServicioEmpresaDao servEmpresaDao;
    private AsignacionVoluntarioDao asignacionVoluntarioDao;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

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
    public void setAsignacionVoluntarioDao(AsignacionVoluntarioDao asignacionVoluntarioDao) {
        this.asignacionVoluntarioDao = asignacionVoluntarioDao;
    }

    // List method

    @RequestMapping("/list")
    public String listDemandantes(Model model) {
        model.addAttribute("demandantes", demandanteDao.getDemandantes());
        return "demandante/list";
    }

    /* Add methods
    *
    */

    @RequestMapping(value = "/add")
    public String addDemandante(Model model) {
        model.addAttribute("demandante", new Demandante());
        return "demandante/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("demandante") Demandante demandante,
                                   BindingResult bindingResult, Model model) {
        // Comprobación de errores
        DemandanteValidator dv = new DemandanteValidator();
        dv.validate(demandante, bindingResult);

        if (bindingResult.hasErrors())
            return "demandante/add";

        // Campos estáticos
        demandante.setRol(ROL_USUARIO.DEMANDANTE);
        demandante.setStatus("SIN REVISAR");
        usuarioDao.addUsuario(demandante);
        demandanteDao.addDemandante(demandante);

        return "redirect:/servVoluntario/add/"+demandante.getNick();
    }

    // Update methods

    @RequestMapping(value = "/update/{nick}", method = RequestMethod.GET)
    public String editDemandante(Model model, @PathVariable String nick) {
        model.addAttribute("demandante", demandanteDao.getDemandante(nick));
        return "demandante/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("demandante") Demandante demandante,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "demandante/update";
        demandanteDao.updateDemandante(demandante);
        return "redirect:list";
    }

    // Delete method

    @RequestMapping(value = "/delete/{nick}")
    public String processDelete(@PathVariable String nick) {
        demandanteDao.deleteDemandante(nick);
        return "redirect:../list";
    }

    // View method
    @RequestMapping(value = "/solicitudes/{nick}")
    public String mostrarSolicitudes(@PathVariable String nick, Model model){
        // Coger todas las solicitudes de pago
        List<ServicioEmpresa> serviciosEmpresa = servEmpresaDao.getServiciosEmpresaDemandante(nick);
        // Coger todas las solicitudes de voluntario
        List<AsignacionVoluntario> serviciosVoluntario = asignacionVoluntarioDao.getServiciosVoluntarioDemandante(nick);

        log.info("TAMAÑO DE LA LISTA VOLUNTARIO: "+serviciosVoluntario.size());
        log.info("TAMAÑO DE LA LISTA EMPRESA: "+serviciosEmpresa.size());

        model.addAttribute("serviciosEmpresa", serviciosEmpresa);
        model.addAttribute("serviciosVoluntario", serviciosVoluntario);

        return "comiteCas/viewDemandante";
    }

}