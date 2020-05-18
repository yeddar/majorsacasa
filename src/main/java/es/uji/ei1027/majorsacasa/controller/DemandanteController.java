package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.AsignacionVoluntarioDao;
import es.uji.ei1027.majorsacasa.dao.DemandanteDao;
import es.uji.ei1027.majorsacasa.dao.ServicioEmpresaDao;
import es.uji.ei1027.majorsacasa.dao.UsuarioDao;
import es.uji.ei1027.majorsacasa.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/demandante")
public class DemandanteController {
    private DemandanteDao demandanteDao;
    private UsuarioDao usuarioDao;
    private ServicioEmpresaDao servEmpresaDao;
    private AsignacionVoluntarioDao asignacionVoluntarioDao;

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
    public String listDemandantes(Model model, HttpSession session) {
        model.addAttribute("demandantes", demandanteDao.getDemandantes());
        session.setAttribute("lastURL", "../list");
        return "demandante/list";
    }

    @RequestMapping("/listSinRevisar")
    public String listDemandantesPendientes(Model model, HttpSession session){
        model.addAttribute("demandantes", demandanteDao.getDemandantesPendientes());
        session.setAttribute("lastURL", "../listSinRevisar");
        return "demandante/listSinRevisar";
    }

    @RequestMapping("/listAceptados")
    public String listDemandantesAceptados(Model model, HttpSession session){
        model.addAttribute("demandantes", demandanteDao.getDemandantesAceptados());
        session.setAttribute("lastURL", "../listAceptados");
        return "demandante/listAceptados";
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
                                   BindingResult bindingResult, HttpSession session) {
        // Comprobación de errores
        DemandanteValidator dv = new DemandanteValidator();
        dv.validate(demandante, bindingResult);

        if (bindingResult.hasErrors())
            return "demandante/add";

        // Campos estáticos
        demandante.setRol(ROL_USUARIO.DEMANDANTE);
        demandante.setStatus("SIN REVISAR");

        session.setAttribute("demandante_registro", demandante);
        return "redirect:/servVoluntario/add";
    }

    // Update methods

    /*
        MODIFICACION DEL PERFIL DE UN DEMANDANTE ESPECIFICO DESDE EL COMITE
    */
    @RequestMapping(value = "/update/{nick}", method = RequestMethod.GET)
    public String editDemandante(Model model, @PathVariable String nick) {
        model.addAttribute("demandante", demandanteDao.getDemandante(nick));
        return "demandante/update";
    }

    /*
        MODIFICACION DEL PERFIL DEDE EL PROPIO DEMANDANTE
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String editDemandante(Model model, HttpSession session) {
        String nick = (String) session.getAttribute("nick");
        model.addAttribute("demandante", demandanteDao.getDemandante(nick));
        return "demandante/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("demandante") Demandante demandante,
            HttpSession session,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "demandante/update";
        demandanteDao.updateDemandante(demandante);

        Usuario user = (Usuario) session.getAttribute("user");

        if (user.getRol().equals("DEMANDANTE")){
            return "redirect:/common/home";
        }
        return "redirect:list";
    }

    // Delete method

    @RequestMapping(value = "/delete/{nick}")
    public String processDelete(@PathVariable String nick, HttpSession session) {
        demandanteDao.deleteDemandante(nick);
        return "redirect:"+session.getAttribute("lastURL");
    }

    // Accept user method

    @RequestMapping(value = "/accept/{nick}")
    public String processAccept(@PathVariable String nick){
        demandanteDao.acceptDemandante(nick);
        return "redirect:../listSinRevisar";
    }

    @RequestMapping(value = "/accept/codAsist/{nick}")
    public String putCodAsist(@PathVariable String nick, Model model){
        model.addAttribute("demandante", nick);
        return "demandante/putCodAsist";
    }

    @RequestMapping(value = "/accept/{nick}", method = RequestMethod.POST)
    public String processPOSTAccept(@RequestParam(value="cod_asist") String cod,
                                    @PathVariable String nick){
        Demandante dem = demandanteDao.getDemandante(nick);
        dem.setCod_asist(cod);
        demandanteDao.updateDemandante(dem);
        demandanteDao.acceptDemandante(nick);
        return "redirect:../listSinRevisar";
    }

    // View method
    @RequestMapping(value = "/solicitudes/{nick}")
    public String mostrarSolicitudes(@PathVariable String nick, Model model){
        // Coger todas las solicitudes de pago
        List<ServicioEmpresa> serviciosEmpresa = servEmpresaDao.getServiciosEmpresaDemandante(nick);
        // Coger todas las solicitudes de voluntario
        List<AsignacionVoluntario> serviciosVoluntario = asignacionVoluntarioDao.getServiciosVoluntarioDemandante(nick);

        model.addAttribute("serviciosEmpresa", serviciosEmpresa);
        model.addAttribute("serviciosVoluntario", serviciosVoluntario);

        return "comiteCAS/viewDemandante";
    }

    @RequestMapping(value = "/viewProfile/{nick}")
    public String verPerfil (@PathVariable String nick, Model model){
        Demandante dem = demandanteDao.getDemandante(nick);
        model.addAttribute("demandante", dem);
        return "demandante/viewProfile";
    }

}