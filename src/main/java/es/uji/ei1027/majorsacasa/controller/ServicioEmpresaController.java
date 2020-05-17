package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.*;
import es.uji.ei1027.majorsacasa.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/servEmpresa")
public class ServicioEmpresaController {
    private ServicioEmpresaDao servEmpDao;
    private ServicioCateringDao servCatDao;
    private ServicioSanitarioDao servSanDao;
    private ServicioLimpiezaDao servLimDao;
    private EmpresaDao empDao;
    private DemandanteDao demandanteDao;
    private FslDao fslDao;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setServicioEmpresaDao(ServicioEmpresaDao servEmpDao) {
        this.servEmpDao = servEmpDao;
    }

    @Autowired
    public void setServicioCateringDao(ServicioCateringDao servCatDao) {
        this.servCatDao = servCatDao;
    }

    @Autowired
    public void setServicioSanitarioDao(ServicioSanitarioDao servSanDao) {
        this.servSanDao = servSanDao;
    }

    @Autowired
    public void setServicioLimpiezaDao(ServicioLimpiezaDao servLimDao) {
        this.servLimDao = servLimDao;
    }

    @Autowired
    public void setEmpresaDao(EmpresaDao empDao) {
        this.empDao = empDao;
    }

    @Autowired
    public void setDemandanteDao(DemandanteDao demandanteDao) {
        this.demandanteDao = demandanteDao;
    }

    @Autowired
    public void setFslDao(FslDao fslDao) {
        this.fslDao = fslDao;
    }

    /* Add methods
     *
     */

    @RequestMapping(value = "/add")
    public String addServicioEmpresa(Model model) {
        model.addAttribute("servCatering", new ServicioCatering());
        model.addAttribute("servSanitario", new ServicioSanitario());
        model.addAttribute("servLimpieza", new ServicioLimpieza());
        return "servicio_empresa/add";
    }

    private String constructor_cadena(String[] dias_semana){
        List dias_lista = new ArrayList<>();
        dias_lista = Arrays.asList(dias_semana);
        String dias = "";

        if(dias_lista.contains("Lunes"))
            dias += 'L';
        else
            dias += '-';
        if(dias_lista.contains("Martes"))
            dias += 'M';
        else
            dias += '-';
        if(dias_lista.contains("Miercoles"))
            dias += 'X';
        else
            dias += '-';
        if(dias_lista.contains("Jueves"))
            dias += 'J';
        else
            dias += '-';
        if(dias_lista.contains("Viernes"))
            dias += 'V';
        else
            dias += '-';

        return dias;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("servCatering") ServicioCatering servCatering,
                                   @ModelAttribute("servSanitario") ServicioSanitario servSanitario,
                                   @ModelAttribute("servLimpieza") ServicioLimpieza servLimpieza,
                                   @RequestParam(value="servicios", required = false) String[] servicios,
                                   @RequestParam(value = "dias_catering", required = false) String[] dias_semana,
                                   @RequestParam(value = "fecha_catering", required = false) String fecha_catering,
                                   @RequestParam(value = "fecha_sanitario", required = false) String fecha_sanitario,
                                   @RequestParam(value = "fecha_limpieza", required = false) String fecha_limpieza,
                                   BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors())
            return "servEmpresa/add";

        if(servicios != null) {
            for (String servicio : servicios) {
                if (servicio.equals("catering")) {
                    ServicioEmpresa servEmpresaCat = new ServicioEmpresa();

                    // AÑADIMOS DEMANDANTE Y EMPRESA
                    servEmpresaCat.setNick_empresa("empresa5"); // ESTATICO
                    servEmpresaCat.setNick_demandante((String) session.getAttribute("nick"));
                    servCatering.setNick_demandante((String) session.getAttribute("nick"));
                    servCatering.setNick_empresa("empresa5"); // ESTATICO

                    // AÑADIMOS VALORES DE FORMULARIO RESTANTES
                    LocalDate f_fin = fecha_catering.equals("") ? null : LocalDate.parse(fecha_catering);
                    servEmpresaCat.setF_fin(f_fin);
                    String dias = constructor_cadena(dias_semana);
                    servCatering.setDias_semana(dias);
                    servEmpresaCat.setServ_status("SIN EVALUAR");

                    // AÑADIMOS A BASE DE DATOS
                    servEmpDao.addServicioEmpresa(servEmpresaCat);
                    servCatDao.addServicioCatering(servCatering);

                } else if (servicio.equals("sanitario")) {
                    ServicioEmpresa servEmpresaSan = new ServicioEmpresa();

                    // AÑADIMOS DEMANDANTE Y EMPRESA
                    servEmpresaSan.setNick_empresa("empresa1"); // ESTATICO
                    servEmpresaSan.setNick_demandante((String) session.getAttribute("nick"));
                    servSanitario.setNick_demandante((String) session.getAttribute("nick"));
                    servSanitario.setNick_empresa("empresa1"); // ESTATICO

                    // AÑADIMOS VALORES DE FORMULARIO RESTANTES
                    LocalDate f_fin = fecha_sanitario.equals("") ? null : LocalDate.parse(fecha_sanitario);
                    servEmpresaSan.setF_fin(f_fin);
                    servEmpresaSan.setServ_status("SIN EVALUAR");

                    // AÑADIMOS A BASE DE DATOS
                    servEmpDao.addServicioEmpresa(servEmpresaSan);
                    servSanDao.addServicioSanitario(servSanitario);

                } else if (servicio.equals("limpieza")) {
                    ServicioEmpresa servEmpresaLim = new ServicioEmpresa();

                    // AÑADIMOS DEMANDANTE Y EMPRESA
                    servEmpresaLim.setNick_empresa("empresa3");
                    servEmpresaLim.setNick_demandante((String) session.getAttribute("nick"));
                    servLimpieza.setNick_demandante((String) session.getAttribute("nick"));
                    servLimpieza.setNick_empresa("empresa3");

                    // AÑADIMOS VALORES DE FORMULARIO RESTANTES
                    LocalDate f_fin = fecha_limpieza.equals("") ? null : LocalDate.parse(fecha_limpieza);
                    servEmpresaLim.setF_fin(f_fin);
                    servEmpresaLim.setServ_status("SIN EVALUAR");

                    // AÑADIMOS A BASE DE DATOS
                    servEmpDao.addServicioEmpresa(servEmpresaLim);
                    servLimDao.addServicioLimpieza(servLimpieza);
                }
            }
        }

        return "redirect:/";
    }

    // List by type method
    @RequestMapping("/listWithType")
    public String listEmpresas(Model model) {
        List<ServicioEmpresa> serviciosEmpresa = servEmpDao.getServiciosEmpresa();
        model.addAttribute("servicios_empresa",serviciosEmpresa );

        HashMap servsTipo = new HashMap();
        for(ServicioEmpresa servicioEmpresa : serviciosEmpresa){
            String nickEmpresa = servicioEmpresa.getNick_empresa();
            Empresa empresa = empDao.getEmpresa(nickEmpresa);
            servsTipo.put(nickEmpresa,empresa.getTipo_empresa());
        }
        model.addAttribute("servsTipo", servsTipo);

        return "servicio_empresa/listWithType";
    }

    /*
    View methods
     */
    @RequestMapping(value = "/view/{nickEmp}/{nickDem}")
    public String view(@PathVariable String nickEmp, @PathVariable String nickDem, Model model){
        String tipo = empDao.getEmpresa(nickEmp).getTipo_empresa();
        ServicioEmpresa servEmp = servEmpDao.getServicioEmpresaStatus(nickDem, nickEmp);
        model.addAttribute("status", servEmp.getServ_status());

        if(tipo.equals("CATERING")){
            ServicioCatering servCat = servCatDao.getServicioCatering(nickEmp, nickDem);
            model.addAttribute("servicioCatering", servCat);
            return "comiteCas/viewServEmpCatering";

        }else if(tipo.equals("SANITARIA")){
            ServicioSanitario servSan = servSanDao.getServicioSanitario(nickEmp, nickDem);
            model.addAttribute("servicioSanitario", servSan);
            return "comiteCas/viewServEmpSanitario";

        }else{
            ServicioLimpieza servLimp = servLimDao.getServicioLimpieza(nickEmp, nickDem);
            model.addAttribute("servicioLimpieza", servLimp);
            return "comiteCas/viewServEmpLimpieza";
        }
    }

    /*
    Accept state
     */
    @RequestMapping(value = "/accept/{nickEmp}/{nickDem}")
    public String acceptState(@PathVariable String nickEmp, @PathVariable String nickDem){
        servEmpDao.setTypeStatus(nickEmp, nickDem, "ESPERA EMPRESA");
        Demandante dem = demandanteDao.getDemandante(nickDem);
        if(servEmpDao.getSinRevisarDemandante(nickDem).size() == 0)
            log.info("Estimado usuario "+ dem.getNombre()+", nos alegra comunicarle que su registro fue aceptado correctamente. A continuación puede proceder al pago de los servicios solicitados mediante el siguiente link. Los servicios se pondrán en marcha en un margen de 24 horas después del pago. Muchas gracias.");
        return "redirect:/demandante/solicitudes/"+nickDem;
    }

    /*
    Cancel state
     */
    @RequestMapping(value = "/cancel/{nickEmp}/{nickDem}")
    public String cancelState(@PathVariable String nickDem, @PathVariable String nickEmp){
        servEmpDao.setTypeStatus(nickEmp, nickDem, "CANCELADO");
        servEmpDao.setF_Fin(nickEmp,nickDem);
        return "redirect:/demandante/solicitudes/"+nickDem;
    }

    @RequestMapping(value = "/cancel/{nickDem}")
    public String cancelByEmpresa(@PathVariable String nickDem, HttpSession session){
        servEmpDao.setTypeStatus((String) session.getAttribute("nick"), nickDem, "CANCELADO");
        servEmpDao.setF_Fin((String) session.getAttribute("nick"),nickDem);
        return "redirect:"+session.getAttribute("lastURL");
    }

    /*
    Feedback methods
    */
    @RequestMapping(value = "/loadFeedback/{nick}")
    public String getFeedbackView(@PathVariable String nick, Model model, HttpSession session){
        // cogemos el tipo de empresa y lo pasamos al modelo
        String tipoEmpresa = empDao.getEmpresa((String) session.getAttribute("nick")).getTipo_empresa();
        model.addAttribute("tipo_empresa", tipoEmpresa);
        if(tipoEmpresa.equals("LIMPIEZA")){
            HashMap<String, List<FranjaServicioLimpieza>> franjas_dem = session.getAttribute("franjas") != null ? (HashMap<String, List<FranjaServicioLimpieza>>) session.getAttribute("franjas") :new HashMap<>();
            if(!franjas_dem.containsKey(nick))
                franjas_dem.put(nick,new ArrayList<>());

            session.setAttribute("franjas", franjas_dem);
            model.addAttribute("franjas_demandante", franjas_dem.get(nick));
        }
        model.addAttribute("demandante", nick);
        return "empresa/putFeedbackEmpresa";
    }

    @RequestMapping(value = "/feedback-catering/{nick}", method = RequestMethod.POST)
    public String setFeedbackCatering(@RequestParam(value="hora_servicio") String hora_servicio,
                                      @PathVariable String nick, HttpSession session) throws ParseException {
        String nickEmp = (String) session.getAttribute("nick");
        // ACTUALIZAMOS EL ESTADO DE LA TABLA SERVICIO EMPRESA
        servEmpDao.setTypeStatus(nickEmp, nick, "ACEPTADO");
        servEmpDao.setF_Ini(nickEmp, nick);
        // ACTUALIZAMOS LOS VALORES DEL FEEDBACK DE LA TABLA SERVICIO CATERING
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        Date d = dateFormat.parse(hora_servicio);
        servCatDao.setFeedback(nickEmp, nick, d);
        return "redirect:/empresa/listPendientes";
    }

    @RequestMapping(value = "/feedback-sanitario/{nick}", method = RequestMethod.POST)
    public String setFeedbackSanitario(@RequestParam(value="dia_visita") String dia_visita,
                                       @RequestParam(value="franja") String franja,
                                       @PathVariable String nick, HttpSession session){
        String nickEmp = (String) session.getAttribute("nick");
        // ACTUALIZAMOS EL ESTADO DE LA TABLA SERVICIO EMPRESA
        servEmpDao.setTypeStatus(nickEmp, nick, "ACEPTADO");
        servEmpDao.setF_Ini(nickEmp, nick);
        // ACTUALIZAMOS LOS VALORES DEL FEEDBACK DE LA TABLA SERVICIO SANITARIO
        char franja_dia = franja.equals("manana") ? 'M':'T';
        servSanDao.setFeedback(nickEmp, nick, LocalDate.parse(dia_visita), franja_dia);
        return "redirect:/empresa/listPendientes";
    }

    @RequestMapping(value = "/feedback-limpieza/{nick}")
    public String setFeedbackLimpieza(@PathVariable String nick, HttpSession session){

        String nickEmp = (String) session.getAttribute("nick");
        // ACTUALIZAMOS EL ESTADO DE LA TABLA SERVICIO EMPRESA
        servEmpDao.setTypeStatus(nickEmp, nick, "ACEPTADO");
        servEmpDao.setF_Ini(nickEmp, nick);
        // ACTUALIZAMOS LOS VALORES DEL FEEDBACK DE LA TABLA SERVICIO LIMPIEZA
        HashMap<String, List<FranjaServicioLimpieza>> franjas_creadas = (HashMap<String, List<FranjaServicioLimpieza>>) session.getAttribute("franjas");
        for(FranjaServicioLimpieza fsl : franjas_creadas.get(nick)){
            fslDao.addFranja(fsl);
        }
        // BORRAMOS DE LA CACHE
        session.removeAttribute("franjas");

        return "redirect:/empresa/listPendientes";
    }
}
