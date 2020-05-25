package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.*;
import es.uji.ei1027.majorsacasa.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/demandante")
public class DemandanteController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private AsignacionVoluntarioDao asigVolDao;

    private EmpresaDao empresaDao;
    private ServicioEmpresaDao servEmpresaDao;
    private ServicioCateringDao servicioCateringDao;
    private ServicioSanitarioDao servicioSanitarioDao;
    private ServicioLimpiezaDao servicioLimpiezaDao;

    private DemandanteDao demandanteDao;
    private VoluntarioDao voluntarioDao;
    private UsuarioDao usuarioDao;
    private AsignacionVoluntarioDao asignacionVoluntarioDao;
    private FsvDao fsvDao;


    private FslDao fslDao;

    @Autowired
    public void setAsigVolDao(AsignacionVoluntarioDao asigVolDao) {
        this.asigVolDao = asigVolDao;
    }

    @Autowired
    public void setFslDao(FslDao fslDao) {
        this.fslDao = fslDao;
    }

    @Autowired
    public void setEmpresaDao(EmpresaDao empresaDao){
        this.empresaDao = empresaDao;
    }

    @Autowired
    public void setDemandanteDao(DemandanteDao demandanteDao) {
        this.demandanteDao = demandanteDao;
    }

    @Autowired
    public void setServicioEmpresaDao(ServicioEmpresaDao servEmpDao) {
        this.servEmpresaDao = servEmpDao;
    }

    @Autowired
    public void setVoluntarioDao(VoluntarioDao voluntarioDao) {
        this.voluntarioDao = voluntarioDao;
    }

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Autowired
    public void setAsignacionVoluntarioDao(AsignacionVoluntarioDao asignacionVoluntarioDao) {
        this.asignacionVoluntarioDao = asignacionVoluntarioDao;
    }

    @Autowired
    public void setServEmpresaDao(ServicioEmpresaDao servEmpresaDao) {
        this.servEmpresaDao = servEmpresaDao;
    }

    @Autowired
    public void setServicioCateringDao(ServicioCateringDao servicioCateringDao) {
        this.servicioCateringDao = servicioCateringDao;
    }

    @Autowired
    public void setServicioSanitarioDao(ServicioSanitarioDao servicioSanitarioDao) {
        this.servicioSanitarioDao = servicioSanitarioDao;
    }

    @Autowired
    public void setServicioLimpiezaDao(ServicioLimpiezaDao servicioLimpiezaDao) {
        this.servicioLimpiezaDao = servicioLimpiezaDao;
    }

    @Autowired
    public void setFsvDao(FsvDao fsvDao) {
        this.fsvDao = fsvDao;
    }


    /*
        List method
     */

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

    /*
    * Add methods
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

    /*
     * Update methods
     */


     // MODIFICACION DESDE EL COMITECAS

    @RequestMapping(value = "/update/{nick}", method = RequestMethod.GET)
    public String editDemandante(Model model, @PathVariable String nick) {
        model.addAttribute("demandante", demandanteDao.getDemandante(nick));
        return "demandante/update";
    }


    // DESDE EL PROPIO DEMANDANTE

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

    /*
     * Delete method
     */

    @RequestMapping(value = "/delete/{nick}")
    public String processDelete(@PathVariable String nick, HttpSession session) {
        demandanteDao.deleteDemandante(nick);
        return "redirect:"+session.getAttribute("lastURL");
    }

    /*
     * Accept user method
     */

    // DEMANDANTE ACEPTADO
    @RequestMapping(value = "/accept/{nick}")
    public String processAccept(@PathVariable String nick){
        demandanteDao.acceptDemandante(nick);
        return "redirect:../listSinRevisar";
    }

    // MOD DEL COD_ASIST
    @RequestMapping(value = "/accept/codAsist/{nick}")
    public String putCodAsist(@PathVariable String nick, Model model){
        model.addAttribute("demandante", nick);
        return "demandante/putCodAsist";
    }

    // POST METHOD
    @RequestMapping(value = "/accept/{nick}", method = RequestMethod.POST)
    public String processPOSTAccept(@RequestParam(value="cod_asist") String cod,
                                    @PathVariable String nick){
        Demandante dem = demandanteDao.getDemandante(nick);
        dem.setCod_asist(cod);
        demandanteDao.updateDemandante(dem);
        demandanteDao.acceptDemandante(nick);
        return "redirect:../solicitudes/"+nick;
    }

    /*
     * views method
     */

    // Demandante view method
    @RequestMapping(value = "/viewProfile/{nick}")
    public String verPerfil (@PathVariable String nick, Model model){
        Demandante dem = demandanteDao.getDemandante(nick);
        model.addAttribute("demandante", dem);
        return "demandante/viewProfile";
    }


    @RequestMapping(value = "/solicitudes/{nick}")
    public String mostrarSolicitudes(@PathVariable String nick, Model model){
        // Acepta demandante si es necesario
        Demandante d =demandanteDao.getDemandante(nick);
        if (d.getStatus().equals("SIN REVISAR"))
            demandanteDao.acceptDemandante(nick);

        // Coger todas las solicitudes de pago
        List<ServicioEmpresa> serviciosEmpresa =
                servEmpresaDao.getServiciosEmpresaDemandante(nick);
        // Coger todas las solicitudes de voluntario
        List<AsignacionVoluntario> serviciosVoluntario =
                asignacionVoluntarioDao.getServiciosVoluntarioDemandante(nick);

        // Cogemos los datos de la franja a mostrar
        HashMap<Integer, FranjaServicioVoluntario> info_franjas = new HashMap<>();
        for (AsignacionVoluntario asignacion : serviciosVoluntario)
            info_franjas.put(asignacion.getId_franja(), fsvDao.getFsv(asignacion.getId_franja()));


        model.addAttribute("serviciosEmpresa", serviciosEmpresa);
        model.addAttribute("serviciosVoluntario", serviciosVoluntario);
        model.addAttribute("info_franjas", info_franjas);

        return "comiteCAS/viewDemandante";
    }


    // Demandante ve solicitudes de pago
    @RequestMapping(value = "/solicitudesPago")
    public String mostrarSolicitudesPago(Model model, HttpSession session) {
        String nick_demandante = (String) session.getAttribute("nick");

        ServicioCatering servicioCatering =
                servicioCateringDao.getServicioCateringByDemandante(nick_demandante);

        ServicioSanitario servicioSanitario =
                servicioSanitarioDao.getServicioSanitarioByDemandante(nick_demandante);

        ServicioLimpieza servicioLimpieza =
                servicioLimpiezaDao.getServicioLimpiezaByDemandante(nick_demandante);


        // Traer cada uno de los servicios empresa del sistema utilizando los dos nicks
        // y un único servEmpresa para cada subtipo especifico

        if (servicioCatering != null) {
            ServicioEmpresa servEmpresaEspCat =
                    servEmpresaDao.getServicioEmpresaStatus(nick_demandante, servicioCatering.getNick_empresa());
            Empresa empresaCat = empresaDao.getEmpresa(servicioCatering.getNick_empresa());

            model.addAttribute("empresaCat", empresaCat);
            model.addAttribute("servEmpresaEspCat", servEmpresaEspCat);
        }

        if (servicioSanitario != null) {
            ServicioEmpresa servEmpresaEspSan =
                    servEmpresaDao.getServicioEmpresaStatus(nick_demandante, servicioSanitario.getNick_empresa());
            Empresa empresaSan = empresaDao.getEmpresa(servicioSanitario.getNick_empresa());

            model.addAttribute("empresaSan", empresaSan);
            model.addAttribute("servEmpresaEspSan", servEmpresaEspSan);
        }

        if (servicioLimpieza != null){
            ServicioEmpresa servEmpresaEspLimp =
                    servEmpresaDao.getServicioEmpresaStatus(nick_demandante, servicioLimpieza.getNick_empresa());
            Empresa empresaLim = empresaDao.getEmpresa(servicioLimpieza.getNick_empresa());

            // Anadir las franjas de limpieza
            List<FranjaServicioLimpieza> franjasLimpieza =
                    fslDao.getFranjasByDemandanteAndEmpresa(nick_demandante, servicioLimpieza.getNick_empresa());
            // Metemos en el modelo
            model.addAttribute("franjasLimpieza", franjasLimpieza);

            model.addAttribute("empresaLim", empresaLim);
            model.addAttribute("servEmpresaEspLimp", servEmpresaEspLimp);
        }

        model.addAttribute("servicioCatering", servicioCatering);
        model.addAttribute("servicioSanitario", servicioSanitario);
        model.addAttribute("servicioLimpieza", servicioLimpieza);


        return "demandante/listServsPago";
    }


    // Demandante ve solicitudes de voluntario

    @RequestMapping(value = "/solicitudesVolun")
    public String mostrarSolicitudes(Model model, HttpSession session){

        String nick_demandante = (String) session.getAttribute("nick");

        List<AsignacionVoluntario> serviciosVoluntario =
                asignacionVoluntarioDao.getByDemandante(nick_demandante);

        HashMap<Integer, Voluntario> mapVoluntarios = new HashMap<>();
        for(AsignacionVoluntario a : serviciosVoluntario) {
            FranjaServicioVoluntario fsv = fsvDao.getFsv(a.getId_franja());
            mapVoluntarios.put(a.getId_franja(), voluntarioDao.getVoluntario(fsv.getNick()));
        }

        model.addAttribute("serviciosVoluntario", serviciosVoluntario);
        model.addAttribute("mapVoluntarios", mapVoluntarios);

        session.setAttribute("lastURL", "/demandante/solicitudesVolun");

        return "demandante/listServsVolun";
    }

    /*
    Pago servicios
     */

    @RequestMapping(value = "/pago-servicios")
    public String pagoServicios(Model model, HttpSession session){
        Demandante demandante = (Demandante) session.getAttribute("demandante_registro");

        model.addAttribute("cantidadCatering", session.getAttribute("cantidadCatering"));
        model.addAttribute("cantidadSanitario", session.getAttribute("cantidadSanitario"));
        model.addAttribute("cantidadLimpieza", session.getAttribute("cantidadLimpieza"));
        model.addAttribute("pagoTotal", session.getAttribute("pagoTotal"));
        model.addAttribute("demandante", demandante);
        return "demandante/pagoServicios";
    }

    @RequestMapping(value = "/gestion-pago")
    public String gestionPago(HttpSession session) {
        Demandante demandante = (Demandante) session.getAttribute("demandante_registro");
        int cantidad = (int) session.getAttribute("pagoTotal");

        log.info("SERVICIO DE PAGO: El usuario " + demandante.getNick() + " realizó el pago de " + cantidad + " euros correctamente.");

        // AÑADIMOS TODA LA INFORMACIÓN A LA BASE DE DATOS
        // DATOS DEL USUARIO
        usuarioDao.addUsuario(demandante);
        demandanteDao.addDemandante(demandante);

        //DATOS DE SERVICIOS VOLUNTARIOS
        ArrayList<AsignacionVoluntario> asignaciones = (ArrayList<AsignacionVoluntario>) session.getAttribute("servicios_demandante_voluntario");
        asignaciones = asignaciones == null ? new ArrayList<>() : asignaciones;
        for (AsignacionVoluntario asignacion : asignaciones) {
            asigVolDao.addAsignacionVoluntario(asignacion);
        }

        // DATOS DE CATERING
        ServicioEmpresa servEmpCat = session.getAttribute("servEmpCat") == null ? null : (ServicioEmpresa) session.getAttribute("servEmpCat");
        ServicioCatering servCat = session.getAttribute("servCat") == null ? null : (ServicioCatering) session.getAttribute("servCat");
        if (servEmpCat != null && servCat != null) {
            servEmpresaDao.addServicioEmpresa(servEmpCat);
            servicioCateringDao.addServicioCatering(servCat);
        }

        // DATOS DE SANITARIO
        ServicioEmpresa servEmpSan = session.getAttribute("servEmpSan") == null ? null: (ServicioEmpresa) session.getAttribute("servEmpSan");
        ServicioSanitario servSan = session.getAttribute("servSan") == null ? null: (ServicioSanitario) session.getAttribute("servSan");
        if(servEmpSan != null && servSan != null){
            servEmpresaDao.addServicioEmpresa(servEmpSan);
            servicioSanitarioDao.addServicioSanitario(servSan);
        }

        // DATOS DE LIMPIEZA
        ServicioEmpresa servEmpLim = session.getAttribute("servEmpLim") == null ? null: (ServicioEmpresa) session.getAttribute("servEmpLim");
        ServicioLimpieza servLim = session.getAttribute("servLim") == null ? null: (ServicioLimpieza) session.getAttribute("servLim");
        if(servEmpLim != null && servLim != null){
            servEmpresaDao.addServicioEmpresa(servEmpLim);
            servicioLimpiezaDao.addServicioLimpieza(servLim);
        }

        session.invalidate();
        return "redirect:/";
    }


}